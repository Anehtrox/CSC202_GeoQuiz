# GeoQuiz — CSC202 Task 1

Android quiz app (Kotlin, MVC + ViewModel) built for CSC202 Mobile App Project, Task 1.

## Feature checklist (from Task 1 rubric)

- [x] 1. App with 5 questions compiles and runs
- [x] 2. TRUE & FALSE buttons behave correctly
- [x] 3. NEXT & PREV buttons behave correctly
- [x] 4. NEXT & PREV buttons have arrow icons
- [x] 5. App uses ViewModel
- [x] 6. App performs correctly after rotation
- [x] 7. CHEAT button behaves correctly
- [x] 8. Cheat activity works properly
- [x] 9. MainActivity shows "Cheating is wrong" iff the user actually cheats
- [x] 10. Anti-cheat: rotating CheatActivity after cheating doesn't clear the cheat flag

## Setup notes

- New project created as **Empty View Activity** (not Empty Activity/Compose), Groovy DSL build files, per Week 2 workshop guidance.
- min SDK 24, target/compile SDK 34, view binding enabled.

## Opening the project

Open this folder directly in Android Studio (`File > Open`). Let it sync Gradle on first open —
the Gradle wrapper jar isn't committed, so Android Studio will offer to regenerate it
(or run `gradle wrapper` once if you have Gradle installed locally).
