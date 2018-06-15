This is a light weight test case management system simply called TC.

Features to be implemented:

1. JWT based authentication (use Redis for storage of tokens) - the algorithm could be as follows:

once a user logs in - a new token is created and is stored in Redis (with a timeout specified when the key is created)
with every next request - the token will be checked against existence in Redis, in cases when the token is not there, the 
user will be asked to login (this might happen in case the user logged out - the token got invalidated or the token simply expired).

OR

to use the benefit of JWT tokens - even if one is invalidated (user logged out and a correspoding cookie was removed - it's fine if a new token is going to be created), the expiration time should be not that far away, so that there would not be too many tokens for the same user.

Probably, I'll go with second approach to use the stateless characteristics of jwt.

Another situation is when user logs in from multiple browsers/ devices - for all of that different tokens would be generated (assuming different exp)

5. Generate a more advanced key for signing

7. Add username to the ResponseDecorator (for the one to be displayed in the ui)

8. Paging

9. Last updated is not always getting updated

10. Add tests

11. Add api documentation (RAML/Swagger/Apiblueprint)

12. Add etag to delete

Some api curls:

###################################################################################################################################

Users specific:

Create user:

curl -X POST -d '{"emailAddress":"bogdan@gmail.com", "userCredentialsDTO":{"username":"danila", "password":"password"}, "userRole":"USER"}' -H "Content-type: application/json" "localhost:8085/users"

Token specific:

Obtain token:

curl -k -X POST -H "Content-type: application/json" -H "Authorization: Basic ZGFuaWxhOnBhc3N3b3Jk" "localhost:8085/api/v1/token"

Note*: using ("danila:password)

Test case specific:

Get a specific test case:

curl -X GET -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYW5pbGEiLCJyb2xlIjoiVVNFUiIsImV4cCI6MTQ2MTk1MjMzNTA2NH0._KMxo3a7QEC_tU3lSaWbeEd4wpz3Pu7n1vn1581KffPBnPzNb_fH-qFj4Gw85wgbZ_8XOWebdeQmi8yDI_E3vw" "localhost:8085/testcases/28"

Create a test case:

curl -X POST -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYW5pbGEiLCJyb2xlIjoiVVNFUiIsImV4cCI6MTQ2MTk1MjMzNTA2NH0._KMxo3a7QEC_tU3lSaWbeEd4wpz3Pu7n1vn1581KffPBnPzNb_fH-qFj4Gw85wgbZ_8XOWebdeQmi8yDI_E3vw" -H "Content-type: application/json" -d '{"title":"some title", "description":"some description"}' "localhost:8085/testcases"
