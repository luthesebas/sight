# sight
REST-Service

Base URL: http://localhost:8080/

Recipe resource:

| URI         | GET             | POST            | PUT             | DELETE          |
|-------------|-----------------|-----------------|-----------------|-----------------|
| recipe      | List of recipes | Create a recipe | Not allowed     | Not allowed     |
| recipe/{id} | A recipe        | Not allowed     | Update a recipe | Delete a recipe |

Instruction resource:

| URI                           | GET                                           | POST                        | PUT                         | DELETE                      |
|-------------------------------|-----------------------------------------------|-----------------------------|-----------------------------|-----------------------------|
| recipe/{id}/instructions      | List of all instructions of a specific recipe | Create a recipe instruction | Not allowed                 | Not allowed                 |
| recipe/{id}/instructions/{id} | A instruction of a specific recipe            | Not allowed                 | Update a recipe instruction | Delete a recipe instruction |
