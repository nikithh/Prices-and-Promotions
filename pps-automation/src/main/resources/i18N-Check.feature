@i18N-Check
Feature: verificateur i18n #i18N Checker

  Scenario: verification de la langue française lors de la traduction #Checking for French language upon translation
    Given accedez a la page de connexion du detaillant #navigate to retailer login page
    When lutilisateur clique sur le selecteur de langue #user clicks on language selector
    Then selectionne le francais #selects french
    Then lutilisateur entre le nom dutilisateur comme "admin" et mot de passe comme "admin" lors de la conversion #user enters username as admin and password as admin upon conversion
