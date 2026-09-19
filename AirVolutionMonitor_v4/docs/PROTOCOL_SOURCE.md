# Protocolo real

A v4 usa o pacote mantido `midea-local==12.0.0`, publicado em 13/09/2026,
para descoberta e comunicação M-Smart/LAN.

API utilizada no gateway:

- `discover(ip_address=...)`
- `device_selector(...)`
- `ac.connect()`
- `ac.attributes`

O projeto Midea AC LAN documenta porta 6444, autenticação por Token/Key e
entidades de AC para temperatura ambiente T1, temperatura da serpentina
interna T2, serpentina externa T3, setpoint, fan e dados de estado.

O APK não incorpora uma implementação paralela dos pacotes Midea. Ele consome
o gateway normalizado, reduzindo o risco de quebrar o protocolo quando a
biblioteca for atualizada.

Para uma leitura real são necessários IP do aparelho e Token/Key válidos.