#-------------------------------------------------------------------------------
# Name:        módulo1
# Purpose:
#
# Author:      noels
#
# Created:     13/11/2025
# Copyright:   (c) noels 2025
# Licence:     <your licence>
#-------------------------------------------------------------------------------

#Ejercicio 2.4
nombre= input("Introduce nombre del Alumno: ")
nota1= float(input("Introduce la nota 1: "))
nota2= float(input("Introduce la nota 2: "))

media=(nota1+nota2)/2

#Ejercicio 2.5
if media>=5:
    aprueba=True
else:
    aprueba=False

print("La nota media del alumno", nombre ,"es", media)
print("Aprueba la asignatura:", aprueba)

#Ejercicio 3.1
año=5
a=-2
if año!=a:
    print(año)
else:
    print(a)



