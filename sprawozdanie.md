## Michał Grobelny

# Sprawozdanie z ćwiczeń z Git-a

#### Pyt. 1
### Jak usunąć pliki związane z IntelliJ (np. folder *.idea*) z GitHub-a?

```
git rm -r --cached .idea *.iml
git commit
git push
```

#### Pyt. 2  
### Zmienić brancha z powrotem na test. Jakie pojawiły się problemy i jak można je rozwiązać? Opisz dwa sposoby.  

Zmiany w pliku pom.xml nie zostały zakomitowane, więc żeby nie zostały utracone, Git blokuje przełączenie gałęzi i podpowiada 2 rozwiązania:  

Rozwiązanie 1: Zakomitować zmiany i dopiero wtedy przełączyć gałąź.  
Rozwiązanie 2: Użyć `git stash` i przełączyć gałąź.

#### Pyt. 3  
### Opisać, jaka jest różnica między `git checkout HEAD~3` a `git reset --hard HEAD~3`  

`git checkout HEAD~3` zmienia zawartość katalogu roboczego do postaci, jaka była 3 commity wstecz, ale nie usuwa commitów z historii - z technicznego punktu widzenia przesuwa tylko wskaźnik HEAD na trzeci commit wstecz (będziemy wtedy w stanie Detached HEAD). To taki "tryb przeglądania wcześniejszego commita". Jeżeli są jakieś zmienione i niezakomitowane pliki, to Git nie pozwoli na wykonanie tej operacji (chyba, że `git checkout -f`).    
`git reset --hard HEAD~3` także przywraca zawartość katalogu roboczego do postaci sprzed 3 commitów, jednak jest to inna operacja. Z technicznego punktu widzenia przesuwa wskaźnik gałęzi (usuwa commity z historii). Niezakomitowane zmiany w plikach zostaną utracone. To podobno najniebezpieczniejsza komenda Git-a - rezultaty są często nieodwracalne.

#### Pyt. 4  
### Jaka jest różnica między `revert` a `reset`?  

`revert` – odtwarza stan z podanego hasha, ale tworzy przy tym nowy commit, czyli żaden commit nie jest usuwany z historii.  
`reset` – odtwarza stan z danego commita, ale usuwa przy tym commity z historii.

Test `revert`:

```
$ git log 4723a0c085c0f102b41dbc5a535fbd8694db9904
commit 4723a0c085c0f102b41dbc5a535fbd8694db9904
Author: Michał Grobelny <michal.a.grobelny@capgemini.com>
Date:   Sat Sep 21 17:01:10 2024 +0200

    Revert "Add developer to pom.xml"

    This reverts commit ccbdee64cccc20d2aaefd5bd33692850274c663e.

commit ccbdee64cccc20d2aaefd5bd33692850274c663e
Author: Michał Grobelny <michal.a.grobelny@capgemini.com>
Date:   Sat Sep 21 12:36:23 2024 +0200

    Add developer to pom.xml
```

#### Pyt. 5  
### Uruchomić komendę `git clean -n`. Przeanalizować, co by sie stało, gdyby nie było `-n`.  

`git clean` usuwa z katalogu roboczego pliki nieśledzone czyli takie, które zostały utworzone w katalogu roboczym, ale nie zostały jeszcze dodane do indeksu komendą `git add`.  

Opcja `-n` to tzw. dry-run czyli tylko informacja o tym, co dana komenda by wykonała, bez faktycznego wykonywania – plik „test” zostałby usunięty z katalogu roboczego.  

#### Pyt. 6  
### `git remote prune -n origin` - przeanalizować, co by się stało, gdyby nie było `-n`.  

Usunięte zostałyby lokalne odwołania do nieistniejących już gałęzi w zdalnym repozytorium (bo np. gałąź została skasowana po mergu do maina).

#### Pyt. 7
### Jak usunąć nieużywanego brancha (lokalnie i zdalnie)?  

Lokalnie:  
`git branch -d <nazwa_galezi_lokalnej>` albo (równoważnie) `git branch --delete <nazwa_galezi_lokalnej>`  
`git branch -D <nazwa_galezi_lokalnej>` albo (równoważnie) `git branch --delete --force <nazwa_galezi_lokalnej>`  

Różnica między opcją `-d` a `-D` polega na tym, że `-d` wymaga, aby kasowana gałąź była w pełni zmergowana z gałęzią zdalną, a `-D` tego nie wymaga. 

Zdalnie:  
`git push origin --delete <nazwa_galezi_zdalnej>` 

#### Pyt. 8  
### Co robi opcja `--no-commit`?  

Opcja `--no-commit` powoduje, że nie końcu operacji (np. `merge` albo `revert`) nie zostanie wykonany automatycznie commit - użytkownik będzie miał możliwość przejrzenia rezultatu (w staging area) przed jego ostatecznym zatwierdzeniem - może to mieć duże znaczenie w przypadku bardziej skomplikowanych operacji, np. dużego merga.