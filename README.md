# Evaporador–Condensador: datasheet reverso e telemetria

Projeto de engenharia reversa controlada para documentar o conjunto split Midea identificado no álbum `GPA CD1 1965 RH`.

## Objetivo

Consolidar fotos, etiquetas, medições, diagrama lógico e requisitos para monitoramento Wi‑Fi de manutenção. O projeto separa evidências **confirmadas**, itens **prováveis**, **hipóteses** e pontos que **precisam ser medidos**.

## Estado atual

As evidências disponíveis confirmam a presença de unidade evaporadora/condensadora Midea, placa de interface/display e módulo sem fio **EU‑SK105**, com registro Anatel visível **071398-21-05648**. A pinagem completa da placa de potência e o protocolo do barramento ainda não estão validados.

| Entrega | Estado |
|---|---|
| Estrutura Git | Pronta |
| Evidências fotográficas iniciais | Incluídas em `photos/` |
| Diagrama lógico | Incluído em `docs/03_arquitetura_logica.mmd` |
| Relatório técnico | Incluído em `docs/04_relatorio_tecnico.md` |
| Pinagem validada | Pendente de fotos e medições |
| Extração Wi‑Fi documentada | Arquitetura preliminar pronta; protocolo a confirmar |

## Organização

- `photos/`: fotos originais organizadas por finalidade.
- `docs/00_PROMPT_ANALISE.md`: prompt refinado para novas rodadas.
- `docs/01_EQUIPAMENTO.md`: cadastro do equipamento e evidências.
- `docs/02_PLANO_FOTOS.md`: checklist de fotos e medições necessárias.
- `docs/03_arquitetura_logica.mmd`: fonte editável do diagrama.
- `docs/04_relatorio_tecnico.md`: data sheet e plano de telemetria.
- `data/`: medições CSV, inventário e tabelas de pinagem.
- `scripts/`: utilitários locais, sem acesso ativo à potência.

## Próximo passo

Adicionar as fotos restantes sem redimensionar nem recomprimir. Priorizar etiquetas completas, frente e verso das placas, conectores em foco, esquema elétrico e identificação de cada chicote. Depois registrar as medições na planilha de campo, sempre com instrumento, unidade, condição e data.

## Segurança

Não energizar ou modificar a placa com base apenas neste repositório. O gateway de manutenção deve ser passivo e galvanicamente isolado. Comandos Wi‑Fi devem permanecer desabilitados até haver manual de serviço, pinagem confirmada e ensaio controlado.
