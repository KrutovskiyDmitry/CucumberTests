Feature: ChangeCity
  Scenario: Поменять город
    Given Открыта страница http://invitro.ru/ и закрыты всплывающие окна
    When Нажать кнопку "Ваш город"
    And Нажать кнопку "Выбрать другой"
    And Выбрать Омск
    Then На главной странице выбран Омск