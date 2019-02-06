# automation has saved my sanity, thanks izik1 for the documentation
import requests
import json
import re

ld_r16_u16 = re.compile("LD (..),u16$")
inc_r16 = re.compile("INC (..)$")
inc_r8 = re.compile("INC (.)$")
dec_r16 = re.compile("DEC (..)$")
dec_r8 = re.compile("DEC (.)$")
ld_r8_r8 = re.compile("LD (.),(.)$")

def parseOp(op):
    out = ""
    if ld_r16_u16.match(op):
        reg = ld_r16_u16.findall(op)[0]
        out = "{cpu: Cpu -> cpu.ld_r16_u16(Register." + reg + ")}"
    elif inc_r16.match(op):
        reg = inc_r16.findall(op)[0]
        out = "{cpu: Cpu -> cpu.inc_r16(Register." + reg + ")}"
    elif inc_r8.match(op):
        reg = inc_r8.findall(op)[0]
        out = "{cpu: Cpu -> cpu.inc_r8(Register." + reg + ")}"
    elif dec_r16.match(op):
        reg = dec_r16.findall(op)[0]
        out = "{cpu: Cpu -> cpu.dec_r16(Register." + reg + ")}"
    elif dec_r8.match(op):
        reg = dec_r8.findall(op)[0]
        out = "{cpu: Cpu -> cpu.dec_r8(Register." + reg + ")}"
    elif ld_r8_r8.match(op):
        dest, src = ld_r8_r8.findall(op)[0]
        out = "{cpu: Cpu -> cpu.ld_r8_r8(Register." + dest + ", Register." + src + ")}"
    else:
        out = "{cpu: Cpu -> }"
    return out

# r = requests.get("https://izik1.github.io/gbops/table/dmgops.json")
# open("ops.json", "r").write(r.text)
# json = r.json()
json = json.loads(open("ops.json","r").read())
template = "op[{}] = Opcode(\"{}\", {}, {}) {}"
out = []
for i, k in enumerate(json["Unprefixed"]):
    code = hex(i)[:2] + hex(i)[2:].upper()
    name = k["Name"]
    length = k["Length"]
    time = k["TCyclesBranch"]
    if(i % 16 == 0):
        out.append("")
    out.append(template.format(code,name,length,time,parseOp(name)))

for i in out:
    print(i)

