        .model     tiny              
        .code                        
        org        100h              
start:  mov ah,9              
        mov dx,offset message 
        int 21h 
        mov       dx,offset buffer
        mov       ah,0Ah
        int       21h 

        xor       di,di         
        xor       ax,ax         
        mov       cl,blength
        xor       ch,ch
        xor       bx,bx
        mov       si,cx
        mov       cl,10
asc2hex:
        mov       bl,byte ptr bcontents[di]
        sub       bx,'0'
        mul       cx       
        add       ax,bx    
        inc       di       
        cmp       di,si    
        jb        asc2hex  
        
        mov bx,ax
        mov ah,9
        cmp bx,01h
        je in_jan                      
        cmp bx,02h
        je in_feb
        cmp bx,03h
        je in_mar        
        cmp bx,04h
        je in_apr        
        cmp bx,05h
        je in_may        
        cmp bx,06h
        je in_jun        
        cmp bx,07h
        je in_jul        
        cmp bx,08h
        je in_aug        
        cmp bx,09h
        je in_sep        
        cmp bx,0Ah
        je in_oct        
        cmp bx,0Bh
        je in_nov        
        cmp bx,0Ch
        je in_dea              
finish:
        int 21h
        mov ah,010h
        int 16h
        ret
                                              
in_jan: mov dx,offset jan 
        jmp finish
in_feb: mov dx,offset feb 
        jmp finish
in_mar: mov dx,offset mar 
        jmp finish
in_apr: mov dx,offset apr 
        jmp finish        
in_may: mov dx,offset may 
        jmp finish        
in_jun: mov dx,offset jun 
        jmp finish        
in_jul: mov dx,offset jul 
        jmp finish        
in_aug: mov dx,offset aug 
        jmp finish        
in_sep: mov dx,offset sep 
        jmp finish        
in_oct: mov dx,offset oct 
        jmp finish        
in_nov: mov dx,offset nov 
        jmp finish        
in_dea: mov dx,offset dea 
        jmp finish        

message db "Input number of a month: $'
jan db 0dh,0ah,"January$"
feb db 0dh,0ah,"February$"
mar db 0dh,0ah,"March$"
apr db 0dh,0ah,"April$"
may db 0dh,0ah,"May$"
jun db 0dh,0ah,"June$"
jul db 0dh,0ah,"Jule$"
aug db 0dh,0ah,"August$"
sep db 0dh,0ah,"September$"
oct db 0dh,0ah,"Octember$"
nov db 0dh,0ah,"November$"
dea db 0dh,0ah,"December$"
buffer   db       6
blength db ?
bcontents:
end     start                        
