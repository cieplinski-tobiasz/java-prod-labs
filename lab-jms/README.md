# Przykład użycia
```bash
gradle clean test fatJar
docker build --tag generator-transakcji .
docker run -v [katalog-do-podpiecia]:/storage generator-transakcji
```

Sam Dockerfile wymaga obecności zbudowanego fatJara.