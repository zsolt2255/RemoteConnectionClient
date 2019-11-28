package com.topin.actions;

public class ComputerInformation extends Base {
    private String prefix = "$Comp = (Get-CimInstance -ClassName Win32_ComputerSystem); $DRole = ($Comp).DomainRole; switch ($DRole) { 0 {$DominRole = 'Standalone Workstation'}; 1 {$DominRole = 'Member Workstation'}; 2 {$DominRole = 'Standalone Server'}; 3 {$DominRole = 'Member Server'}; 4 {$DominRole = 'Backup Domain Controller'}; 5 {$DominRole = 'Primary Domain Controller'};} $PhyMem = [string][math]::Round(($Comp).TotalPhysicalMemory/1GB, 1); $FreePhyMem = [string][math]::Round((Get-CimInstance -ClassName Win32_OperatingSystem).FreePhysicalMemory/1024/1024, 1); $cpux = (Get-WmiObject Win32_Processor).Name; $GBMem = $PhyMem + ' GB Physical Memory (' + $FreePhyMem + ' GB Free)';$hostname=$Comp.Name; $ipV4 = Test-Connection -ComputerName (hostname) -Count 1  | Select -ExpandProperty IPV4Address; $ip = $ipV4.IPAddressToString;$osname=(Get-WMIObject win32_operatingsystem).name;$osVer=[System.Environment]::OSVersion.Version;$osVerMajor=$osVer.Major;$osVerMinor=$osVer.Minor;$osVerBuild=$osVer.Build;$bg=(Get-ItemProperty -Path 'HKCU:\\Control Panel\\Desktop' -Name Wallpaper).Wallpaper; Write-Output \"$FreePhyMem;$PhyMem;$cpux;$hostname;$ip;$osVerMajor.$osVerMinor.$osVerBuild;$bg\"";

    @Override
    public String toString() {
        return this.prefix;
    }
}