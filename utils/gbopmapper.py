# automation has saved my sanity, thanks izik1 for the documentation
import requests
import json
r = requests.get("https://izik1.github.io/gbops/table/dmgops.json")

json = r.json()
template = "op[{}] = Opcode(\"{}\", {}, {}) {}"
out = []
for i, k in enumerate(json["Unprefixed"]):
    code = hex(i)[:2] + hex(i)[2:].upper()
    name = k["Name"]
    length = k["Length"]
    time = k["TCyclesBranch"]
    if(i % 16 == 0):
        out.append("")
    out.append(template.format(code,name,length,time,"{}"))

for i in out:
    print(i)