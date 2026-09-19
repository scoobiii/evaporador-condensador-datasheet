# Extração de sensores Midea — AirVolution 42AFVCI18S5 × 38TVCI18S5



## Objetivo e limite técnico



Este documento combina as fontes do projeto para identificar, extrair, normalizar e validar os sensores do conjunto Springer Midea AirVolution. Ele documenta todos os sensores e sinais conhecidos, mas não afirma que todos estejam disponíveis via Wi-Fi. O manual Midea confirma funções e códigos de diagnóstico, porém não publica pinagem completa nem SDK oficial público para leitura direta de T1, T2 e T3.



> Até existir API/SDK autorizado ou evidência experimental controlada, a integração deve ser somente observacional. Sensores externos HVACLY permanecem separados dos sensores originais Midea.
> 


## Fontes consolidadas



- [Página oficial Midea — AirVolution 18.000 BTU](https://www.midea.com.br/ar-condicionado-split-inverter-18000-btu-airvolution-frio-midea-3/p): modelo, características e manual.
- 
- [Manual oficial Midea — Split Hi Wall Inverter](https://conteudo.midea.com.br/manuais/ar-condicionado-split-inverter-18000-btu-airvolution-frio-midea.pdf): nomenclatura, SmartHome, modo AP, sensores e códigos de erro.
- 
- [Manual Smart Kit EU-SK105/US-SK105](https://konstruktor-pt-cdn.s3.amazonaws.com/sap/entry_04011/Cooling%20Heating%20Portables%20SK105%20WiFi_User%20manual_ENG.pdf): Wi-Fi, alimentação e provisionamento genérico.
- 
- [Midea SmartHome](https://www.midea.com/global/smarthome): recursos do aplicativo.
- 
- [Midea AC LAN](https://github.com/wuwentao/midea_ac_lan): integração comunitária, tokens e limitações.
- 
- [midea-python-client](https://pypi.org/project/midea-python-client/): biblioteca para desumidificador EVA II PRO.
- 
- [docs/08_SENSORES_E_LOGICA.md](./08_SENSORES_E_LOGICA.md), [docs/09_SENSORES_COMPONENTES_MIDEA.md](./09_SENSORES_COMPONENTES_MIDEA.md) e [docs/10_MIDEA_DATASHEETS_API_SDK.md](./10_MIDEA_DATASHEETS_API_SDK.md).
- 


## Identificação do conjunto



| Campo | Valor |

|---|---|

| Evaporadora | 42AFVCI18S5 |

| Condensadora | 38TVCI18S5 |

| Capacidade | 18.000 BTU/h / 5,28 kW |

| Ciclo | Somente frio, inverter |

| Alimentação | 220 V, 1F, 60 Hz |

| Refrigerante | R32 |

| Wi-Fi observado | EU-SK105; compatibilidade a confirmar |



A página oficial informa Produto conectado: Sim, mas também Não possui compatibilidade com o Kit Wi-Fi Springer Midea. Confirmar variante, módulo e firmware na unidade instalada.



## Matriz de sensores e sinais



| ID normalizado | ID Midea/código | Grandeza ou função | Local | Estado | Regra |

|---|---|---|---|---|---|

| midea.t1.ambient | T1 / E4 | Temperatura ambiente | Evaporadora | Documentado | Localizar termistor, curva e chicote. |

| midea.t2.evaporator_coil | T2 / E5 | Temperatura da serpentina evaporadora | Evaporadora | Documentado | Confirmar posição e resistência. |

| midea.t3.condenser_coil | T3 / F2 | Temperatura da serpentina condensadora | Condensadora | Documentado | Confirmar posição e relação com placa. |

| midea.voltage_diagnostic | E2 | Erro de sinal de tensão | Sistema | Evento | Não converter em volts. |

| midea.indoor_fan_speed | E3 | Falha de velocidade do ventilador interno | Evaporadora | Evento | Não inferir RPM. |

| midea.outdoor_fan_speed | F5 | Falha de velocidade do ventilador externo | Condensadora | Evento | Não inferir RPM. |

| midea.high_pressure_protection | p6 | Proteção contra alta pressão | Condensadora | Evento | Não chamar de pressão medida. |

| midea.refrigerant_leak_detection | EC | Detecção de perda de refrigerante | Sistema | Evento | Não calcular carga frigorífica. |



Sinais adicionais: E1 indica falha de comunicação entre unidades; E7, falha entre display e placa principal; E0, erro de processador/EEPROM. Preservar os códigos literalmente.



## Canais possíveis no SmartHome



O manual indica controle de liga/desliga, modo, temperatura configurada, velocidade do ventilador e diagnóstico/check. O monitoramento de energia é condicional ao modelo e ao aplicativo.



| Canal | Estado | Normalização |

|---|---|---|

| smarthome.power_state | Possível | Estado do aplicativo, não potência. |

| smarthome.mode | Possível | Preservar enumeração retornada. |

| smarthome.setpoint | Possível | Temperatura configurada, não T1. |

| smarthome.fan_mode | Possível | Preservar velocidade ou automático. |

| smarthome.fault_code | Possível | Guardar código literal. |

| smarthome.energy | Condicional | Usar apenas quando exibido para esta unidade. |

| smarthome.t1/t2/t3 | Não documentado | Não assumir disponibilidade. |



## EU-SK105 e APIs



O manual genérico informa antena PCB, 2.400–2.483,5 MHz, 0–45 °C, umidade 10–85%, entrada DC 5 V/300 mA, potência menor que 20 dBm e Wi-Fi 2,4 GHz. O manual brasileiro descreve sete pressões na tecla LED até o display apresentar AP. AP é pro







