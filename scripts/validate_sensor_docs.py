#!/usr/bin/env python3

"""Validate consistency rules for the Midea sensor documentation."""

from pathlib import Path

import re



ROOT = Path(__file__).resolve().parents[1]

DOCS = ROOT / 'docs'

TARGETS = [DOCS / name for name in ['08_SENSORES_E_LOGICA.md', '09_SENSORES_COMPONENTES_MIDEA.md', '10_MIDEA_DATASHEETS_API_SDK.md', '11_EXTRACAO_TODOS_SENSORES_MIDEA.md']]

REQUIRED_IDS = ['midea.t1.ambient', 'midea.t2.evaporator_coil', 'midea.t3.condenser_coil', 'midea.voltage_diagnostic', 'midea.indoor_fan_speed', 'midea.outdoor_fan_speed', 'midea.high_pressure_protection', 'midea.refrigerant_leak_detection']

REQUIRED_CODES = ['E0', 'E1', 'E2', 'E3', 'E4', 'E5', 'E7', 'EC', 'F2', 'F5', 'p6']

REQUIRED_TERMS = ['EU-SK105', 'SmartHome', 'somente leitura', 'HVACLY']



def fail(message: str) -> None:
  
    print(f'::error::{message}')
  
    raise SystemExit(1)
  


def main() -> int:
  
    missing = [str(path.relative_to(ROOT)) for path in TARGETS if not path.is_file()]
  
    if missing:
      
        fail('Missing documentation files: ' + ', '.join(missing))
      
    text = '\n'.join(path.read_text(encoding='utf-8') for path in TARGETS)
  
    for item in REQUIRED_IDS:
      
        if item not in text:
          
            fail(f'Missing normalized sensor ID: {item}')
          
    for code in REQUIRED_CODES:
      
        if re.search(rf'(?<![A-Za-z0-9]){re.escape(code)}(?![A-Za-z0-9])', text) is None:
          
            fail(f'Missing Midea diagnostic code: {code}')
          
    for term in REQUIRED_TERMS:
      
        if term.lower() not in text.lower():
          
            fail(f'Missing required documentation term: {term}')
          
    forbidden = ['SDK oficial encontrado', 'pinagem confirmada', 'comando direto confirmado', 'superheat real sem pressão', 'subcooling real sem pressão']
  
    for phrase in forbidden:
      
        if phrase.lower() in text.lower():
          
            fail(f'Unsafe or unsupported claim found: {phrase}')
          
    for path in TARGETS:
      
        for link in re.findall(r'\]\((\./[^)]+)\)', path.read_text(encoding='utf-8')):
          
            if not (path.parent / link).resolve().is_file():
              
                fail(f'Broken local link in {path.relative_to(ROOT)}: {link}')
              
    print(f'Validated {len(TARGETS)} sensor documentation files')
  
    print(f'Validated {len(REQUIRED_IDS)} normalized sensor IDs and {len(REQUIRED_CODES)} diagnostic codes')
  
    print('Documentation consistency checks passed')
  
    return 0
  


if __name__ == '__main__':
  
    raise SystemExit(main())
  































