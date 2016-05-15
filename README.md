# waity

Waity - By Groza Claudiu & Balazs Norbert

Urmatoarele features sunt implementate:
- Autentificarea
- Tratarea cazurilor cand in timpul autentificarii, utilizatori introduce date gresite(fara nume utilizator, fara parola, sau cand ceva date sunt incorecte) 
- Tratarea pornirii aplicatiei fara internet
- Citire NFC/QRCode
- Drepturi pentru NFC
- Tratarea cazului cand dispozitivul nu dispuna de NFC fizic
- Tratarea cazului cand starea NFC-ului se schimba in timpul rularii aplicatiei(ON/OFF)
- Pornirea aplicatiei citind un Tag de NFC: daca utilizatorul e autentificat, poate continua cu citire. Altfel este rugat sa se autentifice
- Notificarile de NFC sunt ignorate in toate activitatile care nu au treaba cu acesta
- Se pot vedea categoriile disponibile
- Se pot vedea produsele disponibile
- Se poate plasa o comanda(cu unu sau mai multe produse din diferite categorii)
- Se poate consulta lista de comenzi
- Se poate cere nota de plata
- In cazul intreruperii aplicatiei in timpul comenzii, continutul cosului este persistat si reluat la repornirea aplicatiei
- Utilizatorul actual este persistat, astfel nu este necesara autentificarea la fiecare pornire a aplicatiei 
- ID-ul mesei actuala este persistata
- Navigation Drawer implementat in zona comenzilor: Se poate vedea cine este autentificat, la ce masa sta si se ofera posibilitatea de logout.



Probabil am mai ratat niste use-caseuri, asa ca va asteptam sa testati aplicatia noastra Waity!
