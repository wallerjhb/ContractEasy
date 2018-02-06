del /q C:\xampp\htdocs\*
for /d %%x in (C:\xampp\htdocs\*) do @rd /s /q "%%x"
xcopy "C:\Users\Alex Waller\workspace\ContractEasyClient\war\*" "C:\Data\Contract Easy\Web" /e /Y
xcopy "C:\Data\Contract Easy\Web\*" "C:\xampp\htdocs" /e /Y
xcopy "C:\Users\Alex Waller\workspace\ContractEasyClient\src\com\contracteasy\server\*" "C:\xampp\htdocs\contracteasyserver" /e /Y