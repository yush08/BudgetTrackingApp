# SmartBudget — Personal Finance Tracker

SmartBudget is a modern Android app built with **Kotlin** and **Jetpack Compose** for managing personal finances. Create budgets, record income and expenses, watch your stats update in real time, and manage your account with Firebase Authentication — all wrapped in a clean **dark / light** themed UI.

Built on **MVVM** with reactive **StateFlow** state management.

---

## Features

- **Authentication** — Email/password sign up, sign in, and password reset via Firebase Auth (with loading and error states); user profiles stored in Cloud Firestore.
- **Onboarding** — Three-step intro with page indicators.
- **Home dashboard** — Live total balance, income/expense/savings summary, savings-rate ring, and a recent-transactions list.
- **Add & delete transactions** — Add income/expense from a single sheet; delete from the Home list. Every change instantly updates the balance, chart, budget progress, and insights (one shared data source).
- **Budgets** — Balance summary, income/expense breakdown, and a **monthly budget-progress bar** that turns amber past 80% and red when over budget.
- **Statistics** — Weekly income-vs-expense **line chart** (Vico) with a legend, plus income/expense/balance summary cards.
- **Insights** — Spending breakdown bars, a savings-rate meter, and smart tips derived from your data.
- **Notifications** — Activity feed generated from your transactions.
- **Profile** — Avatar, live stats strip, working settings (notifications toggle, currency, **dark-mode switch**), and a real log-out.
- **Dark / Light theme** — Runtime toggle from the Home header (sun/moon) or Profile; the whole app recomposes instantly.

---

## Tech Stack

| Area | Choice |
|------|--------|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Architecture | MVVM, Repository pattern, StateFlow / Kotlin Flow |
| Navigation | Navigation Compose |
| Backend | Firebase Authentication, Cloud Firestore |
| Charts | Vico |
| Typography | Outfit (bundled font) |
| Build | Gradle (Kotlin DSL) |

---

## Design System

- **Theme-aware color tokens** in `ui/theme/Color.kt` (`AppBackground`, `AppSurface`, `AppAccent`, `AppTextPrimary`, …) exposed as composable getters backed by a `CompositionLocal`. Swapping the `DarkPalette` / `LightPalette` re-themes every screen with no per-widget changes.
- **Runtime theme switch** via `AppThemeState` (`object` with an observable `isDark`); `MainActivity` reads it and recomposes the app.
- **Outfit** typeface applied app-wide through the Material `Typography`.
- Consistent radii, spacing, and `₹` currency formatting throughout.

> Prefer the color tokens over hardcoded `Color.White` / hex values so both themes stay correct.

---

## Project Structure

```
app/src/main/java/com/example/budgettracking/
├── data/
│   ├── model/           # Transaction, Budget, TransactionType, …
│   └── repository/      # TransactionRepository + seeded singleton store
├── navigation/          # NavGraph + route constants
├── screens/             # Splash, Onboarding, Auth, Home, Budgets,
│                        # Stats, Insights, Notifications, Profile, Add flows
├── ui/
│   ├── components/       # BottomNavBar, FloatingActionButtons
│   └── theme/           # Color (palettes/tokens), Theme, Type, AppThemeState
├── viewmodel/           # Auth, Home, Transaction, Budget ViewModels
└── MainActivity.kt
```

---

## App Flow

```
Splash → Onboarding → Sign In / Sign Up → Home
                                           ├── Stats
                                           ├── Insights
                                           ├── Budgets → Add Budget → Add Transaction
                                           └── Profile → Notifications / Log out
```

---

## Getting Started

### Prerequisites
- Android Studio (latest stable)
- JDK 17
- A Firebase project

### Setup
1. Clone the repo and open it in Android Studio.
2. In the [Firebase console](https://console.firebase.google.com/), create/select a project and add an Android app with package name **`com.example.budgettracking`**.
3. Download **`google-services.json`** and place it in the **`app/`** folder:
   ```
   app/google-services.json
   ```
4. In **Authentication → Sign-in method**, enable **Email/Password**.
5. Sync Gradle and **Run**.

> **Emulator tip:** Firebase Auth needs network + Google Play services. Use an emulator system image labelled **"Google Play"** (not plain AOSP) and confirm the emulator has internet, or you'll see *"A network error … has occurred"* on sign-up.

---

## Notes & Limitations

- Transactions are held in an **in-memory store seeded with sample data**, so the app looks alive on first launch and demonstrates live updates. They are **not yet persisted** to Firestore or a local database (see below).
- Only Auth + user-profile creation use Firebase; financial data is local for now.

## Roadmap

- Persist transactions to Firestore / Room (offline support)
- Persist the theme choice and follow the system setting on first launch
- Expense categories, recurring transactions, budget alerts
- All-transactions screen with search/filter, and CSV export

---

## Author

**Kumar Ayush** — [github.com/yush08](https://github.com/yush08)

## License

For educational and portfolio purposes.
