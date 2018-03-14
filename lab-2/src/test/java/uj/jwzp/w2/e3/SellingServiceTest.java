package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class SellingServiceTest {
    private CustomerMoneyService customerMoneyService;

    @Mock
    private PersistenceLayer persistenceLayer;

    @Mock
    private TransactionService transactionService;

    @Mock
    private DiscountWrapper discountWrapper;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private Item nopromo = new Item("nopromo", new BigDecimal(4));
    private Item promo = new Item("promo", new BigDecimal(5));
    private Customer customer = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");

    @Before
    public void setUp() {
        customerMoneyService = new CustomerMoneyService(persistenceLayer);

        Mockito.when(persistenceLayer.getItemByName(ArgumentMatchers.eq("nopromo")))
                .thenReturn(nopromo);

        Mockito.when(persistenceLayer.getItemByName(ArgumentMatchers.eq("promo")))
                .thenReturn(promo);

        Mockito.when(persistenceLayer.getCustomerById(Mockito.anyInt()))
                .thenReturn(customer);

        Mockito.when(
                discountWrapper.getDiscountForItem(Mockito.any(),
                        Mockito.any())
        ).thenReturn(BigDecimal.ZERO);
    }

    @Test
    public void shouldSellWhenNoWeekendPromotion() {
        //given
        SellingService uut = new SellingService(
                transactionService, customerMoneyService,
                discountWrapper, persistenceLayer
        );

        Mockito.when(transactionService.saveTransaction(Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(Boolean.TRUE);

        //when
        boolean sold = uut.sell(nopromo.getName(), 1, customer.getId());

        //then
        Assert.assertTrue(sold);
        Assert.assertEquals(BigDecimal.valueOf(6), customerMoneyService.getMoney(customer.getId()));
    }

    @Test
    public void shouldSellDuringWeekendPromotionAndItemExpensive() {
        //given
        SellingService uut = new SellingService(
                transactionService, customerMoneyService,
                discountWrapper, persistenceLayer
        );
        Mockito.when(discountWrapper.isWeekendPromotion()).thenReturn(Boolean.TRUE);
        Mockito.when(transactionService.saveTransaction(Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(Boolean.TRUE);

        //when
        boolean sold = uut.sell(promo.getName(), 1, customer.getId());

        //then
        Assert.assertTrue(sold);
        Assert.assertEquals(
                BigDecimal.valueOf(5).add(DiscountWrapper.WEEKEND_DISCOUNT),
                customerMoneyService.getMoney(customer.getId())
        );
    }

    @Test
    public void shouldSellALot() {
        //given
        SellingService uut = new SellingService(
                transactionService, customerMoneyService,
                discountWrapper, persistenceLayer
        );
        customerMoneyService.addMoney(customer.getId(), new BigDecimal(990));
        Mockito.when(transactionService.saveTransaction(Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(Boolean.TRUE);

        //when
        boolean sold = uut.sell(nopromo.getName(), 10, customer.getId());

        //then
        Assert.assertTrue(sold);
        Assert.assertEquals(BigDecimal.valueOf(960), customerMoneyService.getMoney(customer.getId()));
    }

    @Test
    public void shouldSellWhenWeekendPromoAndItemTooCheap() {
        // given
        SellingService uut = new SellingService(
                transactionService, customerMoneyService,
                discountWrapper, persistenceLayer
        );

        Mockito.when(transactionService.saveTransaction(Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(Boolean.TRUE);

        // when
        boolean sold = uut.sell(nopromo.getName(), 1, customer.getId());

        // then
        Assert.assertTrue(sold);
        Assert.assertEquals(BigDecimal.valueOf(6), customerMoneyService.getMoney(customer.getId()));
    }

    @Test
    public void shouldNotSellDuringWeekendPromoAndTransactionCannotBeSaved() {
        // given
        SellingService uut = new SellingService(
                transactionService, customerMoneyService,
                discountWrapper, persistenceLayer
        );

        Mockito.when(transactionService.saveTransaction(Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(Boolean.FALSE);
        Mockito.when(discountWrapper.isWeekendPromotion()).thenReturn(Boolean.TRUE);

        // when
        boolean sold = uut.sell(nopromo.getName(), 1, customer.getId());

        // then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(10), customerMoneyService.getMoney(customer.getId()));
    }

    @Test
    public void shouldNotSellWhenNotEnoughMoney() {
        //given
        SellingService uut = new SellingService(
                transactionService, customerMoneyService,
                discountWrapper, persistenceLayer
        );
        Mockito.when(transactionService.saveTransaction(Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(Boolean.TRUE);

        //when
        boolean sold = uut.sell(nopromo.getName(), 7, customer.getId());

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(10), customerMoneyService.getMoney(customer.getId()));
    }
}
