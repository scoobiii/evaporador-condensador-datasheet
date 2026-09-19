# Datasheets Midea, sensores e APIs/SDKs — 42AFVCI18S5 × 38TVCI18S5



## Identificação



A [página oficial brasileira da Midea](https://www.midea.com.br/ar-condicionado-split-inverter-18000-btu-airvolution-frio-midea-3/p) identifica o conjunto 42AFVCI18S5 × 38TVCI18S5 como AirVolution, 18.000 BTU/h, 220 V, R32. O [manual oficial](https://conteudo.midea.com.br/manuais/ar-condicionado-split-inverter-18000-btu-airvolution-frio-midea.pdf) detalha `42` como evaporadora, `38` como condensadora, `V` inverter, `C` somente frio, `I` revisão R32, `18` 5,28 kW/18.000 BTU/h, `S` Springer Midea e `5` 220 V/1F/60 Hz.



A página oficial informa simultaneamente “Produto conectado: Sim” e “Não possui compatibilidade com o Kit Wi‑Fi Springer Midea”. Essa inconsistência exige confirmação da variante, módulo e firmware instalados; não assumir que todo EU-SK105 seja compatível.



## Sensores e diagnósticos documentados



| Sensor/função | Evidência no manual | Estado de integração |

|---|---|---|

| `T1` — temperatura ambiente | `E4`: aberto ou curto | Documentado; localizar termistor e curva. |

| `T2` — serpentina evaporadora | `E5`: aberto ou curto | Documentado; confirmar posição e chicote. |

| `T3` — serpentina condensadora | `F2`: aberto ou curto | Documentado; confirmar posição e chicote. |

| Tensão | `E2`: erro de sinal | Diagnóstico, não canal Wi‑Fi confirmado. |

| Ventilador interno | `E3`: velocidade fora de controle | Diagnóstico, sinal não publicado. |

| Ventilador externo | `F5`: velocidade fora de controle | Diagnóstico, sinal não publicado. |

| Alta pressão | `p6`: proteção | Não confirma transdutor acessível. |

| Perda de refrigerante | `EC`: detecção | Não substitui medição frigorífica. |



`E1` indica comunicação entre unidades; `E7`, comunicação display/placa principal; `E0`, erro de processador/EEPROM. Códigos de erro não autorizam inferir causas além do manual nem provam exportação via Wi‑Fi.



## Componentes e interfaces



| Componente | Estado | Pendência |

|---|---|---|

| Evaporadora 42AFVCI18S5 | Confirmado | Etiqueta, série e revisão. |

| Condensadora 38TVCI18S5 | Confirmado | Etiqueta, série e revisão. |

| Placa principal/display | Documentado funcionalmente | Part number, revisão e pinagem. |

| Placa inverter | A validar | Confirmar existência e etiqueta. |

| EU-SK105 | Identificado; compatibilidade a validar | Conector, alimentação e firmware. |

| Barramento interno | Não disponível | Não conectar gateway nem injetar comandos. |



## EU-SK105



O [manual genérico do Smart Kit EU-SK105/US-SK105](https://konstruktor-pt-cdn.s3.amazonaws.com/sap/entry_04011/Cooling%20Heating%20Portables%20SK105%20WiFi_User%20manual_ENG.pdf) informa antena PCB, 2.400–2.483,5 MHz, 0–45 °C, umidade 10–85%, entrada DC 5 V/300 mA, potência máxima menor que 20 dBm, Wi‑Fi 2,4 GHz e configuração por SmartHome. O manual brasileiro descreve sete pressões na tecla LED até `AP` e provisionamento dentro da janela indicada. Isso é configuração, não contrato de telemetria.



## API e SDK



Não foi localizado SDK público oficial Midea com endpoints, autenticação, leitura T1/T2/T3 ou acesso direto ao EU-SK105 para este modelo. A [página oficial SmartHome](https://www.midea.com/global/smarthome) descreve o aplicativo e o ecossistema, mas não publica neste material um contrato de API. Solicitar à Midea/assistência autorizada API de parceiro, escopos de leitura, tokens, códigos de falha e suporte ao modelo exato.



O projeto comunitário [midea_ac_lan](https://github.com/wuwentao/midea_ac_lan) usa conta pessoal Meiju/SmartHome e Token/Key para integração local, alerta que esses serviços podem ser encerrados e não é SDK oficial. Usar somente em laboratório isolado, sem armazenar credenciais no repositório. O pacote [midea-python-client](https://pypi.org/project/midea-python-client/) é para o desumidificador EVA II PRO, não para este split.



## Estratégia recomendada



1. Priorizar API/SDK oficial autorizado.
2. 
2. Registrar exportações do SmartHome com origem, timestamp e firmware.
3. 
3. Usar integração comunitária apenas em protótipo isolado.
4. 
4. Usar sensores externos HVACLY para temperatura, corrente, tensão e pressão sem alterar o controlador Midea.
5. 
5. Bloquear engenharia reversa ativa até existir autorização, isolamento e procedimento formal.
6. 


Uma leitura genérica do aplicativo não deve ser chamada `T1`, `T2` ou `T3` sem correspondência documental.



## Referências internas



- [`08_SENSORES_E_LOGICA.md`](./08_SENSORES_E_LOGICA.md)
- 
- [`09_SENSORES_COMPONENTES_MIDEA.md`](./09_SENSORES_COMPONENTES_MIDEA.md)
- 







