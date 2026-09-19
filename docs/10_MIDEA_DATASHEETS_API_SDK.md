# Datasheets Midea, sensores e APIs/SDKs — 42AFVCI18S5 × 38TVCI18S5



## Objetivo



Esta nota consolida as fontes públicas localizadas para o conjunto Springer Midea AirVolution **42AFVCI18S5 × 38TVCI18S5**, incluindo o manual oficial, a página do produto, o manual do módulo EU-SK105 e alternativas de integração via Wi‑Fi.



> **Regra de evidência:** uma API comunitária, uma fotografia de placa ou uma hipótese de protocolo não deve ser tratada como SDK oficial nem como confirmação de pinagem.
> 


## Identificação oficial



A [página brasileira da Midea](https://www.midea.com.br/ar-condicionado-split-inverter-18000-btu-airvolution-frio-midea-3/p) identifica o conjunto como 18.000 BTU/h, 220 V, R32 e 42AFVCI18S5x38TVCI18S5. O [manual oficial](https://conteudo.midea.com.br/manuais/ar-condicionado-split-inverter-18000-btu-airvolution-frio-midea.pdf) detalha a nomenclatura: `42` evaporadora, `38` condensadora, `V` inverter, `C` somente frio, `I` revisão R32, `18` 5,28 kW/18.000 BTU/h, `S` Springer Midea e `5` 220 V/1F/60 Hz.



A página oficial contém uma inconsistência que deve ser resolvida antes da integração: informa **“Produto conectado: Sim”**, mas também **“Não possui compatibilidade com o Kit Wi‑Fi Springer Midea”**. O manual oficial descreve SmartHome e modo AP, mas não comprova que todo módulo EU-SK105 seja compatível com esta revisão.



## Sensores documentados no manual



| Identificador | Grandeza/função | Evidência | Estado |

|---|---|---|---|

| `T1` | Temperatura ambiente da unidade interna | `E4`: T1 aberto ou em curto | Documentado; confirmar componente físico e curva. |

| `T2` | Temperatura da serpentina evaporadora | `E5`: T2 aberto ou em curto | Documentado; confirmar posição, chicote e resistência. |

| `T3` | Temperatura da serpentina condensadora | `F2`: T3 aberto ou em curto | Documentado; confirmar posição, chicote e acesso. |

| — | Sinal de tensão | `E2` | Diagnóstico documentado; não é canal Wi‑Fi confirmado. |

| — | Ventilador evaporadora | `E3` | Falha de velocidade documentada; sinal não exposto. |

| — | Ventilador condensadora | `F5` | Falha de velocidade documentada; sinal não exposto. |

| — | Alta pressão | `p6` | Proteção documentada; não confirma transdutor acessível. |

| — | Perda de refrigerante | `EC` | Diagnóstico documentado; não substitui medição frigorífica. |



Também são relevantes `E1` (comunicação interna/externa), `E7` (display/placa principal) e `E0` (processador/EEPROM). O texto do manual deve ser preservado como diagnóstico; não se deve inferir causa adicional.



## Componentes e interfaces



| Componente | Estado | Observação |

|---|---|---|

| Evaporadora 42AFVCI18S5 | Confirmado | Unidade interna, T1/T2, display e interface local. |

| Condensadora 38TVCI18S5 | Confirmado | Unidade externa, T3, compressor, ventilador e proteção. |

| Placa principal e placa do display | Documentadas funcionalmente | `E7` confirma uma relação de comunicação; pinagem não publicada. |

| Placa inverter | Indicada comercialmente | Confirmar revisão e part number na unidade. |

| Serpentina de cobre/Gold Fin | Especificação oficial | Registrar condição física e manutenção. |

| Módulo Wi‑Fi EU-SK105 | Identificado no projeto; compatibilidade a validar | Registrar etiqueta, conector e firmware. |

| Barramento interno | Não validado | Não conectar gateway nem injetar comandos. |



## Datasheet do EU-SK105



O [manual genérico do Smart Kit EU-SK105/US-SK105](https://konstruktor-pt-cdn.s3.amazonaws.com/sap/entry_04011/Cooling%20Heating%20Portables%20SK105%20WiFi_User%20manual_ENG.pdf) informa: antena impressa em PCB; 2.400–2.483,5 MHz; 0–45 °C; umidade 10–85%; entrada DC 5 V/300 mA; potência máxima <20 dBm; configuração pelo MSmartHome/SmartHome; Wi‑Fi 2,4 GHz; e janela aproximada de 8 minutos para provisionamento.



Esses valores são do manual genérico e não confirmam a compatibilidade elétrica, o firmware ou a exposição dos sensores desta unidade. O modo oficial de configuração descrito no manual brasileiro usa sete pressões na tecla `LED` até o display apresentar `AP`; isso é provisionamento, não uma especificação de telemetria.



## APIs e SDKs localizados



### API/SDK oficial



Não foi localizado um SDK público oficial que documente endpoints, autenticação, escopos, leitura de T1/T2/T3 ou acesso direto ao EU-SK105 para este modelo. A [página oficial Midea SmartHome](https://www.midea.com/global/smarthome) descreve o aplicativo, monitoramento e ecossistema, mas não publica neste material um contrato de API para desenvolvedores.



Solicitar formal
