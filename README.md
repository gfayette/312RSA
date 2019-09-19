# MTH312PGP
This program implements PGP using RSA and Bitwise XOR encryption. All encryption operations were written from scratch.

Sample Output:

-----------Creating Alice and Bob-----------

Generating primes greater than 12345678901234...
p = 12345678901253
q = 1629629614965397
It took 1.227 seconds to generate these prime numbers

Generating keys for Alice with minimum public key size 1234567894...
Public key: 1234567903
Private key: 7365055544325771688837105903
Modulus: 20118883954335351880574942441

Generating primes greater than 2234567890123...
p = 2234567890129
q = 1503864190056857
It took 1.94 seconds to generate these prime numbers

Generating keys for Bob with minimum public key size 123456789...
Public key: 123456791
Private key: 2129060804205970286613951911
Modulus: 3360486630215908407039064553

-----------Exchanging key-----------

Alice is converting the shared key "Shared key" to numeric...
The numeric representation of "Shared key" is
1123366213303089525843

Alice is encrypting with RSA...
1123366213303089525843
Encrypts to
2373932912349398538526477834

Alice is signing the message ...
2373932912349398538526477834
The signature is
5519014902366833196379383390

Bob is verifying the signature ...
5519014902366833196379383390
Is a valid signature for the message
2373932912349398538526477834

Bob is decrypting with RSA...
2373932912349398538526477834
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
12422464668613922815924199819

Bob is verifying the signature ...
12422464668613922815924199819
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
3300433758756732648375947016

Alice is verifying the signature ...
3300433758756732648375947016
Is a valid signature for the message
3185026248754847479

Alice is decrypting with bitwise XOR...
3185026248754847479
Decrypts to
3621341868437861069

Alice is converting 3621341868437861069 back to a String...
The message is
"Message 2"


Process finished with exit code 0
