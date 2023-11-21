Feature: User

  Scenario: User operations
    When I POST to uri "/unburden/api/user/signup" with body
    """
    {
      "deviceName": "iPhone 14",
      "deviceId": "98XJ1L",
      "ip": "192.167.12.1"
    }
    """
    Then the response code is 201
    And I store user token from response
    And the response matches
      | path       | matcher | expected     |
      | user.deviceName | is      | iPhone 14    |
      | user.deviceId   | is      | 98XJ1L       |
      | user.ip         | is      | 127.0.0.1 |

    Then I GET to uri "/unburden/api/user/profile" with user token
    Then the response code is 200
    And the response matches
      | path       | matcher | expected     |
      | deviceName | is      | iPhone 14    |
      | deviceId   | is      | 98XJ1L       |
      | ip         | is      | 127.0.0.1 |