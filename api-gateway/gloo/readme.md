lets register Auth0 upstream

glooctl create upstream static --static-hosts kgignatyev.auth0.com:443 --name auth0-upstream


kubectl label upstream -n gloo-system default-r1-geography-service-6565  discovery.solo.io/function_discovery=enabled


kubectl get upstream -n gloo-system default-r1-geography-service-6565  -o yaml

export T=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlJUUTFOamt5UlRkRVFrSkZNRVkyUkVKRE5VWTBRa0pHTlVJME5VRTNOVFE1UTBKRE5qSkNPUSJ9.eyJnaXZlbl9uYW1lIjoiS29uc3RhbnRpbiIsImZhbWlseV9uYW1lIjoiSWduYXR5ZXYiLCJuaWNrbmFtZSI6ImtnaWduYXR5ZXYiLCJuYW1lIjoiS29uc3RhbnRpbiBJZ25hdHlldiIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS0vQU9oMTRHZ25obVBLbHdhNDJtN1dON0txcWhXd25QOXlaMmZYYXNyMHBIQXI3QSIsImxvY2FsZSI6ImVuIiwidXBkYXRlZF9hdCI6IjIwMjAtMDctMjlUMjE6NDA6MTYuMDgyWiIsImVtYWlsIjoia2dpZ25hdHlldkBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaXNzIjoiaHR0cHM6Ly9rZ2lnbmF0eWV2LmF1dGgwLmNvbS8iLCJzdWIiOiJnb29nbGUtb2F1dGgyfDEwODI0NDQxNTAyOTY3MjMwMzI2NCIsImF1ZCI6IkFqQjFQc3N2VE94WFlEUnBnM2ZzZ0ZJN3A3WXRwemlRIiwiaWF0IjoxNTk2MDU4ODE3LCJleHAiOjE1OTYwOTQ4MTcsIm5vbmNlIjoiUVRBeE5rb3dZVU5pYURCT01XNW1NSEJ0VTBoM1kxWmpOekJXY0dwVFIzZHFkbjVpWld4QmFIbHBjdz09In0.eqn1XZa-7-JHW85hPL1ZGomjd8mrQUy3NaiRWP2D5QUxoQQEPBMGmSllbeJ7-ISoekyn-BwAVu_GPMKd5yjFnePYLmLQUhYTeyvKnHGH0Uj7V6nzHDxC5jW5oyH5ELj3ssT3JIN9bLAoZeIeVb9sn-Ve_w3up4fQlagTNwPjTDqphDVrdwkrNJUbc52qB1TluJjGdnB6q_ZPFoorilmI3VdJ2nn3uobxePQEwTFdrcsH7oHSPw5wSB3rq-4Xm0lst7v4EdBEZYtVA6Hf70Tu2kLy44xoryX1RqwV4eX90IBqLbE4_R9ptSM5AeKRK-rEk74ltc4eZoJJERqIBBrH7g

 grpcurl -plaintext  \
   -H 'Host: geography' \
   -H "Authorization: Bearer $T"  \
   geography:9090   list


 grpcurl -plaintext  \
   -H 'Host: geography' \
   -H "Authorization: Bearer $T"  \
   geography:80   list
