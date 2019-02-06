# automation has saved my sanity, thanks izik1 for the documentation
import requests
import json
import re

ld_r16_u16 = re.compile("LD (..),u16$")
ld_r16_r8 = re.compile("LD \((..)\),(.)$")
inc_r16 = re.compile("INC (..)$")
inc_r8 = re.compile("INC (.)$")
dec_r16 = re.compile("DEC (..)$")
dec_r8 = re.compile("DEC (.)$")
ld_r8_u8 = re.compile("LD (.),u8$")
rlc = re.compile("RLC(.)$")
ld_r8_r8 = re.compile("LD (.),(.)$")
add_a_r8 = re.compile("ADD A,(.)$")
addc_a_r8 = re.compile("ADC A,(.)$")
sub_a_r8 = re.compile("SUB A,(.)$")
subc_a_r8 = re.compile("SBC A,(.)$")
and_a_r8 = re.compile("AND A,(.)$")

def parseOp(op):
    out = ""
    if ld_r16_u16.match(op):
        reg = ld_r16_u16.findall(op)[0]
        out = "{cpu: Cpu -> cpu.ld_r16_u16(Register." + reg + ")}"
    elif ld_r16_r8.match(op):
        reg1, reg2 = ld_r16_r8.findall(op)[0]
        out = "{cpu: Cpu -> cpu.ld_r16_r8(Register." + reg1 + ", Register." + reg2 + ")}"
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
    elif ld_r8_u8.match(op):
        reg = ld_r8_u8.findall(op)[0]
        out = "{cpu: Cpu -> cpu.ld_r8_u8(Register." + reg + ")}"
    elif rlc.match(op):
        reg = rlc.findall(op)[0]
        out = "{cpu: Cpu -> cpu.rlc(Register." + reg + ")}"
    elif ld_r8_r8.match(op):
        dest, src = ld_r8_r8.findall(op)[0]
        out = "{cpu: Cpu -> cpu.ld_r8_r8(Register." + dest + ", Register." + src + ")}"
    elif add_a_r8.match(op):
        reg = add_a_r8.findall(op)[0]
        out = "{cpu: Cpu -> cpu.add_a_r8(Register." + reg + ")}"
    elif addc_a_r8.match(op):
        reg = addc_a_r8.findall(op)[0]
        out = "{cpu: Cpu -> cpu.addc_a_r8(Register." + reg + ")}"
    elif sub_a_r8.match(op):
        reg = sub_a_r8.findall(op)[0]
        out = "{cpu: Cpu -> cpu.sub_a_r8(Register." + reg + ")}"
    elif subc_a_r8.match(op):
        reg = subc_a_r8.findall(op)[0]
        out = "{cpu: Cpu -> cpu.subc_a_r8(Register." + reg + ")}"
    elif and_a_r8.match(op):
        reg = and_a_r8.findall(op)[0]
        out = "{cpu: Cpu -> cpu.and_a_r8(Register." + reg + ")}"
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

