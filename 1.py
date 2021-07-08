import json

niska =  "{\"APF3_AN1_43\":11502}"
niska2 = "{'APF3_AN1_43': 11502}"

data = json.loads(niska2)
mapa = dict(data)

print(mapa)