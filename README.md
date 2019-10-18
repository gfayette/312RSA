# MTH312PGP
This program implements PGP using RSA and Bitwise XOR encryption. All encryption operations were written from scratch.

Sample Output:

-----------Creating Alice and Bob-----------

Generating primes greater than 12345678901234...
p = 12345678901253
q = 5185185138526279

Generating keys for Alice with minimum public key size 1234567894...
Public key: 1234567895
Private key: 57286162354607100261026675351
Modulus: 64014630763794496724386527587

Generating primes greater than 2234567890123...
p = 2234567890129
q = 969802464316051

Generating keys for Bob with minimum public key size 123456789...
Public key: 123456791
Private key: 291660537904385214090611111
Modulus: 2167089446528622894099160579

-----------Exchanging key-----------

Alice is converting the shared key "Shared key" to numeric...
The numeric representation of "Shared key" is
1123366213303089525843

Alice is encrypting with RSA...
1123366213303089525843
Encrypts to
1861573995300283437234894661

Alice is signing the message ...
1861573995300283437234894661
The signature is
33548698120675405680982121943

Bob is verifying the signature ...
33548698120675405680982121943
Is a valid signature for the message
1861573995300283437234894661

Bob is decrypting with RSA...
1861573995300283437234894661
Decrypts to
1123366213303089525843

Bob is converting 1123366213303089525843 back to a String...
The shared key is
"Shared key"

-----------Exchanging message-----------

Alice is converting the message "Message 1" to numeric...
The numeric representation of "Message 1" is
3549284274399933133

Alice is encrypting with bitwise XOR...
3549284274399933133
Encrypts to
3401199030868631287

Alice is signing the message ...
3401199030868631287
The signature is
37738288054701499954967546172

Bob is verifying the signature ...
37738288054701499954967546172
Is a valid signature for the message
3401199030868631287

Bob is decrypting with bitwise XOR...
3401199030868631287
Decrypts to
3549284274399933133

Bob is converting 3549284274399933133 back to a String...
The message is
"Message 1"

-----------Exchanging message-----------

Bob is converting the message "Message 2" to numeric...
The numeric representation of "Message 2" is
3621341868437861069

Bob is encrypting with bitwise XOR...
3621341868437861069
Encrypts to
3185026248754847479

Bob is signing the message ...
3185026248754847479
The signature is
1175666778308456391353766194

Alice is verifying the signature ...
1175666778308456391353766194
Is a valid signature for the message
3185026248754847479

Alice is decrypting with bitwise XOR...
3185026248754847479
Decrypts to
3621341868437861069

Alice is converting 3621341868437861069 back to a String...
The message is
"Message 2"
