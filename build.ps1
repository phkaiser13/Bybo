<#
.SYNOPSIS
    build.ps1 - Compila o projeto e gera o instalador MSI.
.DESCRIPTION
    Este script automatiza o processo de build do projeto ByBo no Windows.
    Ele executa o Maven para limpar, compilar, testar e empacotar a aplicação.
    Como o perfil do 'pom.xml' é ativado no Windows, um instalador .msi
    será gerado no diretório 'bybo-app/target/dist'.
.NOTES
    Certifique-se de que o Maven e o JDK 17 estão instalados e configurados
    corretamente no seu PATH.
#>

Write-Host "Iniciando o build do projeto ByBo..."

# Executa o comando do Maven para limpar o projeto e criar os pacotes.
# -B (batch mode) executa o Maven em modo não interativo.
mvn -B clean package

# Verifica o código de saída do Maven.
if ($LASTEXITCODE -eq 0) {
    Write-Host "Build concluído com sucesso!" -ForegroundColor Green
    Write-Host "Instalador MSI gerado em bybo-app/target/dist/"
} else {
    Write-Host "O build falhou. Verifique o output do Maven para mais detalhes." -ForegroundColor Red
    exit 1
}