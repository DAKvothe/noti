Feature: API Example

  @tagGet
  Scenario Outline: Client makes call to GET
    Given client makes call to GET with <id>
    When client receives GET status code <status>
    Then client receives response <otherInfo>

    Examples:
      | id | status | otherInfo   |
      | 2  | 200    | other info2 |
      | 3  | 200    | other info3 |
      | 1  | 404    |             |

  @tagPage
  Scenario Outline: Client makes call to PAGE
    Given client makes call to GET with page <page> size <size>
    Then client receives pageable status code <status>

    Examples:
      | page | size | status |
      | 0    | 1    | 200    |

  @tagPost
  Scenario Outline: Client makes call to POST
    Given client makes call to POST with "<someInfo>"
    When client receives POST status code <status>
    Then client receives POST response "<someInfo>"

    Examples:
      | someInfo | status |
      | info1    | 201    |
      | info2    | 201    |
      |          | 201    |

  @tagPut
  Scenario Outline: Client makes call to PUT
    Given client calls PUT with <id> and "<someInfo>"
    When client receives PUT status code <status>
    Then client receives PUT code <id> and "<someInfo>"

    Examples:
      | id | someInfo | status |
      | 2  | info2    | 200    |
      | 3  | info3    | 200    |
      | 9  | info4    | 200    |

  @tagDelete
  Scenario Outline: Client makes call to DELETE
    Given client makes call to DELETE with code <id>
    Then client receives status code <status>

    Examples:
      | id | status |
      | 5  | 204    |