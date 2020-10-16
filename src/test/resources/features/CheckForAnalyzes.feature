Feature: CheckForAnalyzes
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