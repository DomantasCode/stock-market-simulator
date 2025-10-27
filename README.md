# 📈 BIRŽOS MINI-SIMULIATORIUS

Java projektas su JavaFX grafine sąsaja, kuris simuliuoja akcijų biržos prekybą. Žaidėjas turi pradinį balansą ir gali pirkti/parduoti akcijas per 10 sandorių.

## 🎯 Projekto aprašymas

Šis projektas yra biržos simuliatorius su grafinė sąsaja, kuriame žaidėjas:
- Pradeda su **1000 EUR** balansu
- Prekiauja **TECH** akcijomis
- Atlieka **10 sandorių** (BUY/SELL/HOLD)
- Stebi kainas grafinėje kainų diagramoje
- Siekia uždirbti kuo daugiau pelno

### Realizuotos funkcijos ✅
- ✅ Vieno aktyvo (akcijos) simuliacija
- ✅ Pradinis balansas: 1000 EUR
- ✅ 10 sandorių žaidimas
- ✅ BUY/SELL/HOLD veiksmai
- ✅ JavaFX grafinė sąsaja
- ✅ Sandorių mokesčiai (0.1% tik BUY/SELL)
- ✅ Bankroto tikrinimas
- ✅ Galutinių rezultatų ekranas
- ✅ Validacija ir klaidų valdymas

### OO koncepcijos ir Design Patterns
**OO koncepcijos:**
- **Inheritance**: `Stock extends Asset`
- **Encapsulation**: private laukai, getters/setters
- **Polymorphism**: `List<Asset>` portfelyje
- **Abstraction**: `PriceStrategy` interface

**Design Patterns:**
- **Factory Method**: `TransactionFactory` sandorių kūrimui
- **Strategy Pattern**: `PriceStrategy` kainų generavimui

**Clean Code:**
- Metodai ≤ 30 eilučių
- Klasės ≤ 200 eilučių
- DRY principas
- Konstantos vietoj "magic numbers"

**Unit testai:**
- 22 JUnit 5 testai
- 100% praėjimo rezultatas

## 📁 Projekto struktūra

```
src/main/java/org/example/stockmarket/
├── MainGUI.java                       # Aplikacijos įėjimo taškas
├── model/                             # Duomenų modeliai
│   ├── Asset.java                     # Abstract class aktyvams
│   ├── Stock.java                     # Akcijos (extends Asset)
│   ├── Portfolio.java                 # Portfelis (balansas, akcijos)
│   ├── Transaction.java               # Sandoris
│   └── GameState.java                 # Žaidimo būsena
├── service/                           # Verslo logika
│   ├── strategy/
│   │   ├── PriceStrategy.java         # Strategy interface
│   │   ├── RandomPriceStrategy.java   # Atsitiktinis kainų kitimas
│   │   └── TrendPriceStrategy.java    # Kainų kitimas su trendu
│   ├── TransactionFactory.java        # Factory pattern
│   ├── TransactionValidator.java      # Validacija
│   └── MarketSimulatorGUI.java        # Žaidimo logika
├── ui/
│   └── GraphicalUI.java               # JavaFX grafinė sąsaja
└── util/
    ├── Constants.java                 # Konstantos
    ├── TransactionType.java           # Enum: BUY, SELL, HOLD
    └── GameStatus.java                # Enum: PLAYING, FINISHED, BANKRUPT
```

## 🚀 Kaip paleisti

**Per Maven (Paprasčiausia):**
```bash
mvn clean javafx:run
```

**Per IntelliJ IDEA:**
1. Atidaryti terminalą (Alt+F12)
2. Įvykdyti: `mvn javafx:run`

### Testų paleidimas

Per terminalą:
```bash
mvn test
```

## 🎮 Kaip žaisti

### Žaidimo taisyklės
1. Žaidimas pradedamas su **1000 EUR** balansu
2. Viena akcija: **TECH**, pradinė kaina **50 EUR**
3. Po kiekvieno veiksmo kaina keičiasi **±5%**
4. Žaidimas trunka **10 sandorių**
5. Už BUY/SELL sandorius imamas **0.1% mokestis**

### Veiksmai
- **PIRKTI** - pirkti akcijas (įveskite kiekį)
- **PARDUOTI** - parduoti akcijas (įveskite kiekį)
- **PRALEISTI** - praleisti ėjimą (kaina keičiasi, bet nėra mokesčio)

### Žaidimo pabaiga
- ✅ Atlikus 10 sandorių - pasirodo "RODYTI REZULTATUS" mygtukas
- ❌ Jei turtas nukrenta žemiau 50 EUR (bankrotas)

### Pavyzdys

```
═══════════════════════════════════════════════
📈 BIRŽOS MINI-SIMULIATORIUS
═══════════════════════════════════════════════
Sandoriai: 5/10

Akcijos informacija:
  Kaina: 52.30 EUR
  Pokytis: +2.30 EUR (+4.6%)

Tavo portfelis:
  Pinigai: 450.00 EUR
  Akcijų: 10 vnt
  Akcijų vertė: 523.00 EUR
  Bendras turtas: 973.00 EUR
═══════════════════════════════════════════════
```

## 📊 Testų rezultatai

```
Tests run: 22, Failures: 0, Errors: 0, Skipped: 0
```

**Detalūs rezultatai:**
- PortfolioTest: 7 testai ✅
- StockTest: 5 testai ✅
- TransactionValidatorTest: 6 testai ✅
- RandomPriceStrategyTest: 4 testai ✅

## 🎓 Įgyvendinti programavimo principai

### SOLID
- **Single Responsibility**: kiekviena klasė turi vieną atsakomybę
- **Open/Closed**: lengvai pridedami nauji Asset tipai ir PriceStrategy
- **Dependency Inversion**: priklausomybė nuo abstrakcijų (PriceStrategy)

### Design Patterns
- **Factory Method**: centralizuotas sandorių kūrimas
- **Strategy Pattern**: skirtingos kainų generavimo strategijos

### Clean Code
- Trumpi metodai (≤30 eilučių)
- Aiškūs pavadinimai
- Komentarai tik kur reikalinga
- Konstantos vietoj "magic numbers"
- DRY principas

## 📝 Pagrindinės funkcijos

- ✅ **JavaFX grafinė sąsaja** su moderniu dizainu
- ✅ **Interaktyvus kainų grafikas** (LineChart) realiu laiku
- ✅ **Spalvotas UI** - skirtingos spalvos veiksmams
- ✅ **Mygtukai** - lengvas naudojimas
- ✅ **Real-time atnaujinimai** - portfolio ir akcijų kainos
- ✅ **Vizualūs pranešimai** - sėkmės/klaidų pranešimai
- ✅ **"RODYTI REZULTATUS" mygtukas** - po 10 sandorių
- ✅ **Sandorių mokesčiai** (0.1% tik BUY/SELL)
- ✅ **TrendPriceStrategy** - kainų kitimas su trendu
- ✅ **Validacija** - neleidžia neigiamų kiekių, patikrina balansą
- ✅ **Klaidų valdymas**
- ✅ **Unit testai** (22 testai, 100% praėjimas)

## 🛠️ Technologijos

- **Java 23**
- **Maven** - projekto valdymas
- **JavaFX 23** - grafinė sąsaja
- **JUnit 5** - unit testai

## 👨‍💻 Autorius

Projektas sukurtas akademiniais tikslais, demonstruojant:
- OO programavimą
- Design Patterns
- Clean Code principus
- Unit testavimą
- Maven projekto valdymą
- JavaFX GUI kūrimą

## 📄 Licencija

Šis projektas yra skirtas švietimo tikslams.
