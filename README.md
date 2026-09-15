# Evaporador–Condensador: datasheet reverso e telemetria

Projeto de engenharia reversa controlada para documentar o conjunto split Springer Midea AirVolution identificado como **42AFVCI18S5 x 38TVCI18S5**, 18.000 BTU/h, 220 V, 60 Hz e R32.

## Objetivo

Consolidar fotos, documentação oficial, etiquetas, medições, diagrama lógico e requisitos para monitoramento Wi‑Fi de usuário e manutenção. O projeto separa evidências **confirmadas**, itens **prováveis**, **hipóteses** e pontos que **precisam ser medidos**.

## Decisão técnica atual

A recomendação é uma solução híbrida. O aplicativo Midea SmartHome permanece como interface do usuário final. A manutenção usa um gateway ESP32/Arduino com sensores externos, isolamento galvânico, MQTT/TLS e dashboard próprio para gestores e técnicos. O gateway não comanda a placa de potência na primeira versão.

Não foi localizada uma documentação pública oficial da Midea Brasil oferecendo SDK de manutenção, API aberta de telemetria ou protocolo da placa para este modelo. O projeto prevê solicitar formalmente à Midea uma API/SDK de parceiro. Integrações comunitárias são referências de laboratório, não interfaces oficiais.

Sem sensor de pressão, o sistema não calcula superheat ou subcooling termodinâmicos reais. Ele pode registrar apenas indicadores indiretos e deve adicionar pressão e temperatura compatíveis com R32 quando um técnico habilitado fizer o comissionamento.

## Estado atual

As evidências confirmam a unidade evaporadora/condensadora Midea, a placa de interface/display e o módulo sem fio **EU‑SK105**, com registro Anatel visível **071398-21-05648**. A pinagem completa da placa de potência e o protocolo do barramento ainda não estão validados.

| Entrega | Estado |
|---|---|
| Estrutura Git | Pronta |
| Evidências fotográficas iniciais | Incluídas em `photos/` |
| Documentação oficial | Referenciada em `docs/05_DOCUMENTACAO_OFICIAL/` |
| Interfaces de usuário e manutenção | Especificadas em `docs/06_INTERFACES/` |
| Sensores e lógica | Especificados em `docs/08_SENSORES_E_LOGICA.md` |
| Midea DevTools/SDK | Investigado em `docs/09_MIDEA_DEVTOOLS.md` |
| Backlog de app e gateway | Priorizado em `docs/10_BACKLOG.md` |
| Inventário de conectores/chicotes | Template criado; IDs aguardam fotos e medições |
| Diagrama lógico | Incluído em `docs/03_arquitetura_logica.mmd` |
| Relatório técnico | Incluído em `docs/04_relatorio_tecnico.md` |
| Pinagem validada | Pendente de fotos e medições |
| Extração Wi‑Fi | Arquitetura somente leitura; protocolo a confirmar |

## Organização

- `photos/`: fotos originais organizadas por finalidade.
- `docs/00_PROMPT_ANALISE.md`: prompt mestre para novas rodadas.
- `docs/01_EQUIPAMENTO.md`: cadastro do equipamento e evidências.
- `docs/02_PLANO_FOTOS.md`: checklist de fotos e medições necessárias.
- `docs/03_arquitetura_logica.mmd`: fonte editável do diagrama.
- `docs/04_relatorio_tecnico.md`: data sheet e plano de telemetria.
- `docs/05_DOCUMENTACAO_OFICIAL/`: fontes oficiais de usuário, instalação, operação, manutenção e SmartHome.
- `docs/06_INTERFACES/`: interfaces do usuário, manutenção, EU-SK105 e conectores/chicotes.
- `docs/07_LINHA_DO_TEMPO.md`: sequência e estado das etapas.
- `docs/08_SENSORES_E_LOGICA.md`: sensores, controle, monitoramento e cálculo frigorífico.
- `docs/09_MIDEA_DEVTOOLS.md`: SDK, APIs e alternativas de integração.
- `docs/10_BACKLOG.md`: backlog priorizado para gestor, técnico e gateway.
- `data/`: medições CSV e inventários.

## Próximo passo

Adicionar as fotos restantes sem redimensionar nem recomprimir. Priorizar etiquetas completas, frente e verso das placas, conectores em foco, esquema elétrico e identificação de cada chicote. Depois registrar as medições na planilha de campo, sempre com instrumento, unidade, condição e data.

## Segurança

Não energizar ou modificar a placa com base apenas neste repositório. O gateway de manutenção deve ser passivo e galvanicamente isolado. Comandos Wi‑Fi devem permanecer desabilitados até haver manual de serviço, pinagem confirmada e ensaio controlado.
