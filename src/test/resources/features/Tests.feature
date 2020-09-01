Feature: test Invitro

  Scenario: Прокликать все пункты меню
    Given Открыта страница http://invitro.ru/radiology/ и закрыты всплывающие окна
    When Прокликать все пункты и подпункты меню


  Scenario: Поменять город
    Given Открыта страница http://invitro.ru/ и закрыты всплывающие окна
    When Нажать кнопку "Ваш город"
    And Нажать кнопку "Выбрать другой"
    And Выбрать Омск
    Then На главной странице выбран Омск


    Scenario: Проверить результаты анализов
      Given Открыта страница http://invitro.ru/ и закрыты всплывающие окна
      When Нажать кнопку "Получить результаты анализов"
      Then Проверить доступность всплывающего окна
      When Нажать кнопку "Найти результаты"
      Then Проверить наличие сообщения Поля Код ИНЗ, Дата рождения, Фамилия обязательны для заполнения.
      When Ввести в поле inz значение 231231231
      And Ввести в поле born значение 11122000
      And Ввести в поле surname значение Тест
      And Нажать кнопку "Найти результаты"
      Then Проверить наличие сообщения Ваши результаты анализов не найдены. Пожалуйста, измените параметры и повторите поиск

    Scenario: Проверить стоимость услуги в корзине
      Given Открыта страница http://invitro.ru/ и закрыты всплывающие окна
      When Перейти на страницу выбора анализов
      And Выбрать Клинический анализ крови: общий анализ, лейкоформула, СОЭ (с микроскопией мазка крови при наличии патологических сдвигов)
      And Запомнить стоимость услуги
      And Добавить услугу в корзину
      And Перейти в корзину
      Then Сравнить стоимость услуги и стоимость заказа в корзине


     Scenario: Поиск анализов по коду
        Given Открыта страница http://invitro.ru/ и закрыты всплывающие окна
        When Выполнить поиск 1515

     Scenario Outline: Выбор разделов в шапке
        Given Открыта страница https://www.invitro.ru/ и закрыты всплывающие окна
        When Выбрать <menuName>
        Then Проверить что текущая страница <site>
       Examples:
         | menuName             |site                             |
         |Врачам                |https://www.invitro.ru/doctors/  |
         |Франчайзинг           |https://www.invitro.ru/franchize/|
         |Корпоративным клиентам|https://www.invitro.ru/medical/  |
         |Прессе                |https://www.invitro.ru/about/    |
         |Пациентам             |https://www.invitro.ru/          |



