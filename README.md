# Keretrendszer Beadando IMDB
## Infók
* Oktató: TZS
* OS: MacOSX Mojave
* ProjektFeladat: IMDB_Database
* Code_Coverage: 80%
* Adatbázis dump tömörítve (gzip) és a start.sh fájlban autómatikusan feldolgozva
* Sorvégződés (sorvége_jel): LF
* A kód innen való letöltés után, tökéletesen lefut, ha valami nem sikerült akkor nálad van a hiba. 

## Indítás

* Elindítod a dockert.
* Terminálban betallózod a mappát majd a következő command segítségével elindítod az adatbázist:<br> **docker-compose up db** <br>
* Miután sikeresen lefutott állítsd le. <br>**ctrl+c vagy cmd+c**<br>
* Ezt követően indítsd újra a dockered és az adatbázis autómatikusan indulni fog.
* A következő feladatod az az, hogy elindítsd terminálból a backendet. Ehhez a <br>**docker-compose up backend**<br> Parancsot használd.
* Ha módosítottál a fájlban akkor mindíg buildeld újra <br>(**docker-compose build backend majd docker-compose up backend**)<br>
* Ha lefutott ezen a linken meg tudod tekinteni a swaggered: <a href="http://localhost:8080/api/swagger-ui.html">🌱</a>

<div style="text-align: center; font-size: 28px;">☕️ GLHF ☕️<div> 