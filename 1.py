import json

niska1 = "APF3_AN1_43"

x = niska1.split('_')
print(x)
novo = ""
for i in range(len(x)-1):
    novo += x[i]

print(novo)
