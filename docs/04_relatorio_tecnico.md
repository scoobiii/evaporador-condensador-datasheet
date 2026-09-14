# Data sheet preliminar e arquitetura de monitoramento Wi‑Fi

**Equipamento analisado:** conjunto split identificado no álbum como `GPA CD1 1965 RH`, com unidade Midea visível nas fotografias. **Data:** 14 de setembro de 2026. **Autor:** Manus AI.

## 1. Resultado executivo

A análise visual confirma uma unidade evaporadora/condensadora de ar-condicionado split, uma placa frontal de interface/display e um módulo de comunicação sem fio marcado como **Anatel 071398-21-05648** e **EU-SK105**. O álbum também mostra conjuntos de cobre, conectores e módulos encapsulados na unidade externa. A serigrafia completa da placa de potência, os códigos dos termistores, a pinagem do barramento entre unidades e as grandezas de corrente e pressão não estão legíveis nas imagens fornecidas.

Por esse motivo, este documento é um **data sheet de engenharia reversa preliminar**, não um esquema elétrico autorizado pelo fabricante. Nenhum fio deve ser conectado ao circuito de potência com base apenas neste documento. A extração recomendada é passiva, galvanicamente isolada e subordinada às proteções originais.

## 2. Evidências visuais e nível de confiança

| Item | Evidência observada | Confiança | Como validar |
|---|---|---:|---|
| Tipo de sistema | Evaporadora mural e condensadora externa em fotos do mesmo conjunto | Alta | Conferir etiqueta completa do modelo |
| Fabricante visível | Marca Midea na unidade externa | Alta | Fotografar etiqueta de identificação em foco |
| Módulo sem fio | Módulo branco com marca Anatel `071398-21-05648` e identificação `EU-SK105` | Alta | Confirmar código em foto ortogonal |
| Interface interna | Placa verde com display de sete segmentos, conector frontal e receptor infravermelho | Alta | Fotografar frente e verso sem reflexo |
| Placa de potência | Compartimento e módulos encapsulados visíveis, mas sem pinagem legível | Baixa | Remover tampa somente por técnico habilitado |
| Sensores | Devem existir termistores de ambiente e serpentina; não há identificação confiável de cada chicote | Média para a existência, baixa para a pinagem | Rastrear fios e medir resistência fora de tensão |
| Barramento entre unidades | Há chicotes entre evaporadora e condensadora, mas a função dos condutores não está determinada | Baixa | Usar manual de serviço e osciloscópio isolado |

## 3. Data sheet funcional proposto

| Bloco | Entradas | Processamento | Saídas | Dados úteis para manutenção |
|---|---|---|---|---|
| Interface evaporadora | Teclas, receptor IR, temperatura ambiente, temperatura da serpentina | MCU da unidade interna | Display, ventilador, comando de modo e setpoint | Modo, setpoint, temperatura medida, velocidade do ventilador, código de falha |
| Controle térmico | Termistores, estado de proteção, comunicação interna | Algoritmo original do fabricante | Pedido de compressor, ventiladores e válvula | Tempo de ciclo, diferencial térmico, partida e parada |
| Potência condensadora | Comandos do barramento, sensores da unidade externa | Relé, inversor ou módulo de potência, conforme variante | Compressor, ventilador externo e válvula | Corrente, frequência/rotação, temperatura de descarga, estado de proteção |
| Módulo Wi‑Fi | Interface serial ou proprietária com a placa interna | Firmware do módulo EU‑SK105 | Aplicativo e rede local/nuvem | Estado exposto pelo fabricante, conectividade e comandos autorizados |
| Gateway de manutenção | Leituras isoladas da placa e sensores complementares | Aquisição, normalização, retenção e regras | MQTT/TLS, banco e alertas | Tendências, eventos, falhas intermitentes e evidências de manutenção |

## 4. Arquitetura da lógica

O diagrama editável está em [`arquitetura_logica.mmd`](./arquitetura_logica.mmd). A lógica deve ser dividida em três domínios:

1. **Controle original.** A placa original continua responsável por termorregulação, intertravamentos, proteção contra sobrecorrente, proteção térmica e comunicação entre as unidades.
2. **Aquisição de manutenção.** Um gateway separado observa sinais disponíveis por interface documentada ou por sensores externos. Ele não deve substituir termistores, pressostatos, relés ou proteções originais.
3. **Telemetria.** O gateway publica dados normalizados, grava histórico local e envia somente os eventos necessários. Comandos remotos devem permanecer desabilitados até que a pinagem e o protocolo tenham sido confirmados pelo manual de serviço.

> Regra de segurança: a telemetria deve falhar para o estado seguro. Perder Wi‑Fi não pode impedir a refrigeração nem manter um atuador energizado.

## 5. Pontos de medição recomendados

| Grandeza | Método preferencial | Frequência inicial | Uso de manutenção |
|---|---|---:|---|
| Temperatura ambiente | Termistor externo calibrado ou leitura documentada da placa | 10 s | Conforto, erro de sensor e deriva |
| Temperatura de entrada/saída do evaporador | Dois sensores de contato ou leitura validada dos termistores | 10 s | Estimar troca térmica e congelamento |
| Temperatura de descarga | Sensor de contato preso corretamente ao tubo, isolado do ambiente | 10 s | Indício de sobrecarga, falta de fluxo ou restrição |
| Temperatura do condensador | Sensor de contato na região definida pelo fabricante | 10 s | Condensação anormal e sujeira |
| Corrente do compressor | TC ou sensor Hall certificado, instalado sem abrir o circuito | 1 s bruto, 10 s agregado | Partida, sobrecarga e tendência |
| Tensão de alimentação | Transdutor isolado e certificado | 1 s bruto, 10 s agregado | Sub/sobretensão e diagnóstico de rede |
| Estado dos ventiladores | Leitura de sinal documentado ou sensor de rotação | 1–10 s | Falha de partida e intermitência |
| Pressão de sucção/descarga | Transdutores próprios de refrigeração, instalados por profissional habilitado | 1–10 s | Diagnóstico de carga, restrição e compressor |
| Códigos de falha | Protocolo documentado ou captura passiva | Evento | Correlação com data, hora e condição |
| Comunicação Wi‑Fi | RSSI, latência, uptime e motivo de desconexão | 60 s | Separar defeito do equipamento de defeito da rede |

## 6. Extração via Wi‑Fi

### 6.1 Caminho A: usar o módulo original

O módulo **EU‑SK105** deve ser tratado inicialmente como uma interface fechada. O procedimento correto é identificar, no manual de serviço, se a placa disponibiliza uma API local, MQTT, Modbus, protocolo serial ou somente comunicação com aplicativo/nuvem. Não se deve fazer engenharia reversa ativa do rádio nem transmitir comandos desconhecidos.

Quando houver API documentada, o gateway deve consultar apenas os campos permitidos e registrar o instante de aquisição. Os dados devem ser transformados para uma estrutura estável, por exemplo:

```json
{
  "device_id": "gpa-cd1-1965-rh",
  "ts": "2026-09-14T15:35:30-03:00",
  "source": "wifi_module",
  "mode": "cool",
  "setpoint_c": 23.0,
  "room_temp_c": 25.4,
  "fan_level": "auto",
  "alarm_code": null,
  "wifi_rssi_dbm": -61,
  "quality": "observed"
}
```

O campo `quality` deve distinguir **observed** (lido diretamente), **derived** (calculado) e **estimated** (inferido). Valores inferidos não podem ser exibidos como medição de pressão, corrente ou temperatura de descarga.

### 6.2 Caminho B: gateway passivo complementar

Se o módulo original não expuser as grandezas de manutenção, usar um gateway Wi‑Fi separado, alimentado por fonte isolada de baixa tensão. O gateway coleta sensores externos e interfaces galvanicamente isoladas. O protocolo recomendado é **MQTT sobre TLS**, com autenticação por certificado ou senha forte, tópicos separados para telemetria e comandos, e comandos desativados por padrão.

Exemplo de tópicos:

```text
hvac/gpa-cd1-1965-rh/telemetry
hvac/gpa-cd1-1965-rh/events
hvac/gpa-cd1-1965-rh/maintenance
hvac/gpa-cd1-1965-rh/health
```

A mensagem de manutenção deve conter valor, unidade, origem, qualidade, timestamp e estado do sensor. O gateway deve manter uma fila local para pelo menos 24 horas quando a rede estiver indisponível.

### 6.3 O que é permitido monitorar

É apropriado monitorar temperaturas, estados, códigos de falha, ciclos, tempo ligado, conectividade e grandezas obtidas por sensores externos certificados. Também é apropriado calcular tendências, como aumento gradual da temperatura de descarga ou redução do diferencial térmico do evaporador.

Não é apropriado declarar que a placa fornece pressão, corrente, carga de refrigerante ou eficiência energética sem confirmar que existe sensor correspondente e sem calibrar a cadeia de medição. Um código exibido no display não deve ser convertido para causa raiz sem o manual do modelo.

## 7. Regras de alarme e suporte à manutenção

| Regra | Condição | Ação sugerida |
|---|---|---|
| Perda de comunicação | Sem atualização por mais de três períodos esperados | Registrar evento; não alterar o controle original |
| Sensor inválido | Valor fora da faixa física ou variação impossível | Marcar `quality=invalid` e solicitar inspeção |
| Tendência térmica | Descarga subindo ou diferencial do evaporador caindo por horas | Abrir alerta de manutenção preventiva |
| Partida anormal | Corrente elevada sem estabilização no intervalo esperado | Verificar tensão, compressor, capacitor/inversor e ventilação |
| Congelamento | Temperatura da serpentina abaixo do limite definido no manual | Verificar fluxo de ar, filtro, carga e sensor |
| Alta temperatura | Descarga/condensador acima do limite do fabricante | Verificar sujeira, ventilador, restrição e carga |
| Falha recorrente | Mesmo código em mais de N ciclos | Correlacionar com temperatura, tensão e tempo de operação |

Os limites numéricos devem ser preenchidos a partir do manual do modelo e das medições de comissionamento. Não se devem copiar limites genéricos de outro equipamento.

## 8. Procedimento de comissionamento

Antes de energizar o sistema, fotografar a etiqueta completa, os dois lados de cada placa, todos os conectores e a rota de cada chicote. Desligar e bloquear a alimentação antes de qualquer continuidade ou resistência. Medir termistores somente fora de tensão. Confirmar aterramento, isolação e proteção contra contato acidental.

Na primeira partida, registrar simultaneamente temperatura ambiente, setpoint, modo, tensão, corrente, temperaturas de tubos, estados dos ventiladores e códigos de falha. Comparar as leituras do gateway com instrumentos calibrados. Aceitar o canal somente após documentar erro, unidade, localização do sensor e data de calibração.

## 9. Prompt refinado para execução e atualização

```text
Analise as fotos e os documentos do conjunto split Midea identificado como GPA CD1 1965 RH. Produza um data sheet preliminar da placa da evaporadora e da condensadora, separando rigorosamente: (a) fatos legíveis nas imagens; (b) inferências funcionais; (c) itens não confirmados. Identifique fabricante, modelo, códigos de placa, conectores, serigrafia, sensores, atuadores, barramento entre unidades, módulo Wi-Fi e proteções. Não invente pinagem, tensão, corrente, pressão, protocolo ou limite de alarme.

Monte um diagrama Mermaid da arquitetura da lógica com sensores de temperatura, pressão quando existente, proteção original, MCU, placa de potência, compressor, ventiladores, válvula, interface/display, módulo Wi-Fi e gateway de manutenção. Separe o controle original da aquisição passiva. Especifique quais sinais podem ser observados sem interferir na operação e quais exigem manual de serviço, instrumento isolado ou técnico habilitado.

Documente uma solução de extração e monitoramento via Wi-Fi para suporte de manutenção e para o usuário. Inclua: modelo de dados com timestamp, unidade, origem e qualidade; MQTT sobre TLS ou API documentada; fila local durante perda de rede; histórico; dashboard; alertas; retenção; segurança; calibração; comissionamento; diagnóstico de sensores, ventiladores, compressor, tensão, temperatura, comunicação e códigos de falha. Diferencie valores observados, derivados e estimados. Mantenha comandos remotos desabilitados por padrão e preserve todas as proteções originais.

Entregue em português técnico, com tabelas, limitações, riscos elétricos e lista objetiva das próximas fotos e medições necessárias para converter o esquema preliminar em pinagem validada.
```

## 10. Limitações e próximos dados necessários

Para converter este documento em um esquema de pinagem e um data sheet definitivo, são necessárias fotos nítidas e perpendiculares da placa de potência, frente e verso, da etiqueta do modelo completo, dos conectores com numeração e do esquema colado na tampa. Também são necessários os valores nominais dos termistores, o manual de serviço e a identificação do tipo de compressor.

A referência visual utilizada foi o álbum compartilhado pelo usuário [1].

## Referências

[1]: https://photos.app.goo.gl/JY2Sv6bRB3zofGmQ6 "Álbum compartilhado do equipamento GPA CD1 1965 RH"
