# Sensores e componentes específicos — Midea AirVolution 42AFVCI18S5 × 38TVCI18S5



## Escopo e regra de evidência



Este inventário separa a arquitetura física Midea da telemetria externa HVACLY e das atividades PMOC. O conjunto documentado é Springer Midea AirVolution, 18.000 BTU/h, 220 V, 60 Hz e R32. Um sensor instalado externamente pelo HVACLY não é um sensor original Midea.



Estados usados: `CONFIRMADO` (foto, etiqueta, manual ou medição rastreável), `DOCUMENTADO` (manual descreve a função, mas a peça ainda precisa ser localizada), `PROVÁVEL`, `A VALIDAR` e `NÃO DISPONÍVEL`.



## Inventário principal



| Componente/função | Unidade | Estado | Validação pendente |

|---|---|---|---|

| Evaporadora 42AFVCI18S5 | Interna | `CONFIRMADO` | Conferir etiqueta, série e revisão. |

| Condensadora 38TVCI18S5 | Externa | `CONFIRMADO` | Conferir etiqueta, série e revisão. |

| Sensor T1 — ambiente | Evaporadora | `DOCUMENTADO` | Localizar termistor, curva, resistência e chicote. |

| Sensor T2 — serpentina evaporadora | Evaporadora | `DOCUMENTADO` | Confirmar posição, fixação e conexão. |

| Sensor T3 — serpentina condensadora | Condensadora | `DOCUMENTADO` | Confirmar posição, fixação e conexão. |

| Placa principal | Interna/externa conforme revisão | `DOCUMENTADO` | Part number, revisão, conectores e pinagem. |

| Placa display/interface | Evaporadora | `DOCUMENTADO` | Fotos frente/verso, revisão e sinais. |

| Placa inverter | Condensadora | `A VALIDAR` | Confirmar existência, etiqueta e estágio de potência. |

| Compressor | Condensadora | `PROVÁVEL` | Registrar part number e medições externas. |

| Ventilador interno/externo | Ambas | `DOCUMENTADO` como função | Tipo de motor, controle e sinais. |

| Serpentina de cobre/Gold Fin | Condensadora | `CONFIRMADO` como especificação | Registrar condição, corrosão e limpeza. |

| Filtro com íons de prata | Evaporadora | `CONFIRMADO` como recurso | Registrar manutenção; não é sensor. |

| Módulo sem fio EU-SK105 | Interface Wi‑Fi | `IDENTIFICADO; compatibilidade a validar` | Etiqueta, conector, alimentação e firmware. |

| Barramento interno | Interunidades/placas | `NÃO DISPONÍVEL` | Não conectar gateway nem injetar comandos. |



## Sensores e códigos do manual



O manual oficial relaciona `E4` a T1 aberto/em curto, `E5` a T2 aberto/em curto e `F2` a T3 aberto/em curto. Também registra `E2` (sinal de tensão), `E3` (velocidade do ventilador da evaporadora), `F5` (velocidade do ventilador da condensadora), `EC` (detecção de perda de refrigerante) e `p6` (proteção contra alta pressão). `E1` indica falha de comunicação entre unidades e `E7` falha entre display e placa principal.



Esses códigos confirmam funções de diagnóstico, mas não provam que os valores brutos de T1/T2/T3 ou pressão estejam disponíveis via Wi‑Fi. Não renomear uma temperatura genérica do aplicativo como `T1`, `T2` ou `T3` sem correspondência documentada.



## EU-SK105 e conectividade



O manual genérico do Smart Kit EU-SK105/US-SK105 informa antena PCB, 2.400–2.483,5 MHz, 0–45 °C, umidade 10–85%, entrada DC 5 V/300 mA e potência de transmissão menor que 20 dBm. A configuração usa SmartHome, Wi‑Fi 2,4 GHz e modo de provisionamento; no manual brasileiro, sete pressões na tecla LED levam o display a `AP`.



Esses dados não confirmam compatibilidade elétrica, firmware ou protocolo para esta unidade. Registrar o EU-SK105 como componente de conectividade identificado, não como porta aberta de telemetria.



## Sensores externos HVACLY



| Canal | Grandeza | Componente | Estado | Limite |

|---|---|---|---|---|

| `ambient_reference` | Temperatura ambiente | Sensor externo | Opcional | Não substitui T1. |

| `evaporator_inlet/outlet` | Temperatura do ar | Sensor externo | A validar | Não bloquear o fluxo. |

| `suction_line` | Temperatura de sucção | Termopar/contato | A validar | Fixar e isolar; não é superheat. |

| `liquid_line` | Temperatura de líquido | Termopar/contato | A validar | Não é subcooling sem pressão. |

| `discharge_line` | Temperatura de descarga | Sensor de contato | A validar | Não confundir com sensor original. |

| `voltage/current_power` | Tensão, corrente e potência | Transdutor isolado/medidor | A validar | Usar categoria e calibração adequadas. |

| `suction/discharge_pressure` | Pressão R32 | Manifold/transdutor compatível | Não disponível | Somente profissional habilitado. |



Sem pressão medida, não calcular superheat ou subcooling termodinâmicos. O gateway deve permanecer somente leitura e não comandar compressor, válvula, ventilador ou barramento Midea.



## Registro mínimo



```yaml

id: midea-42afvci18s5-component-001

category: sensor | board | module | connector | protection

asset: 42afvci18s5-38tvci18s5

location: evaporadora | condensadora | externo

status: CONFIRMADO | DOCUMENTADO | PROVÁVEL | A VALIDAR | NÃO DISPONÍVEL

source: foto | etiqueta | manual | medição | catálogo

observations: descrição objetiva

unknowns: pontos não comprovados

safety_notes: cuidados e limitações

``
