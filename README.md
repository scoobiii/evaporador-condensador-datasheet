# Evaporador–Condensador: datasheet reverso e telemetria

Projeto de engenharia reversa controlada para documentar o conjunto split Springer Midea AirVolution identificado como **42AFVCI18S5 x 38TVCI18S5**, 18.000 BTU/h, 220 V, 60 Hz e R32, conforme identificação do projeto e fontes oficiais referenciadas.

## Objetivo

Consolidar fotos, documentação oficial, etiquetas, medições, diagrama lógico e requisitos para monitoramento Wi‑Fi de usuário e manutenção. O projeto separa evidências **confirmadas**, itens **prováveis**, **hipóteses** e pontos que **precisam ser medidos**.

## Estado atual

As evidências confirmam a unidade evaporadora/condensadora Midea, a placa de interface/display e o módulo sem fio **EU‑SK105**, com registro Anatel visível **071398-21-05648**. A documentação oficial do produto e os manuais foram referenciados em `docs/05_DOCUMENTACAO_OFICIAL/`. A pinagem completa da placa de potência e o protocolo do barramento ainda não estão validados.

| Entrega | Estado |
|---|---|
| Estrutura Git | Pronta |
| Evidências fotográficas iniciais | Incluídas em `photos/` |
| Documentação oficial | Referenciada e separada por finalidade |
| Interfaces de usuário e manutenção | Especificadas em `docs/06_INTERFACES/` |
| Inventário de conectores/chicotes | Template criado; IDs aguardam fotos e medições |
| Diagrama lógico | Incluído em `docs/03_arquitetura_logica.mmd` |
| Relatório técnico | Incluído em `docs/04_relatorio_tecnico.md` |
| Pinagem validada | Pendente de fotos e medições |
| Extração Wi‑Fi | Arquitetura somente leitura; protocolo a confirmar |

## Organização

- `photos/`: fotos originais organizadas por finalidade.
- `docs/00_PROMPT_ANALISE.md`: prompt refinado para novas rodadas.
- `docs/01_EQUIPAMENTO.md`: cadastro do equipamento e evidências.
- `docs/02_PLANO_FOTOS.md`: checklist de fotos e medições necessárias.
- `docs/03_arquitetura_logica.mmd`: fonte editável do diagrama.
- `docs/04_relatorio_tecnico.md`: data sheet e plano de telemetria.
- `docs/05_DOCUMENTACAO_OFICIAL/`: fontes oficiais de usuário, instalação, operação, manutenção e SmartHome.
- `docs/06_INTERFACES/`: interfaces do usuário, manutenção, EU-SK105 e conectores/chicotes.
- `docs/07_LINHA_DO_TEMPO.md`: sequência e estado das etapas.
- `data/`: medições CSV e inventários.

## Próximo passo

Adicionar as fotos restantes sem redimensionar nem recomprimir. Priorizar etiquetas completas, frente e verso das placas, conectores em foco, esquema elétrico e identificação de cada chicote. Depois registrar as medições na planilha de campo, sempre com instrumento, unidade, condição e data.

## Segurança

Não energizar ou modificar a placa com base apenas neste repositório. O gateway de manutenção deve ser passivo e galvanicamente isolado. Comandos Wi‑Fi devem permanecer desabilitados até haver manual de serviço, pinagem confirmada e ensaio controlado.
