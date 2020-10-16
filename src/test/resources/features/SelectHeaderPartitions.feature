Feature: SelectHeaderPartitions
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
