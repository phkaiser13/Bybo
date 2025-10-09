#!/bin/bash
#
# build.sh - Compila o projeto e gera os instaladores nativos.
#
# Este script automatiza o processo de build do projeto ByBo,
# executando o Maven para limpar, compilar, testar e empacotar
# a aplicação. Ao final, um instalador nativo (e.g., .deb, .rpm)
# será gerado no diretório `bybo-app/target/dist`.
#

echo "Iniciando o build do projeto ByBo..."

# Executa o comando do Maven para limpar o projeto e criar os pacotes.
# -B (batch mode) executa o Maven em modo não interativo.
mvn -B clean package

# Verifica o código de saída do Maven.
if [ $? -eq 0 ]; then
  echo "Build concluído com sucesso!"
  echo "Instalador gerado em bybo-app/target/dist/"
else
  echo "O build falhou. Verifique o output do Maven para mais detalhes."
  exit 1
fi