'''
Created on Jul 9, 2019
@author: mitos
'''
import re
import requests
import PySimpleGUI as sg
regex = '(\d{5})([- ])?(\d{4})?'


def setZip():
    while True:
        try:
            code = re.match(regex, sg.PopupGetText('Zipcode', 'Please input a zipcode').strip()).group()
            print(f'Match mech = {code}')
        #If input is not of ints, throw exception
        except Exception:
            sg.Popup("Illegal input: Please enter a zipcode")
        else:#Zipcode is valid
            break
    return code


def retrieveWeather(zipcode):
    forecast=requests.get('http://api.openweathermap.org/data/2.5/weather?zip='+zipcode+'&APPID=b35975e18dc93725acb092f7272cc6b8&units=imperial');
    return forecast.json()


def printForecast(fc):
    return f'Current Temperature: {fc["main"]["temp"]}\nLow/High {fc["main"]["temp_min"]}/{fc["main"]["temp_max"]}\nHumidity: {fc["main"]["humidity"]}%'


def main():
    zipcode = setZip()
    try:
        forecast = retrieveWeather(zipcode)
        sg.Popup(f'City: {forecast["name"]}', printForecast(forecast))
    except:
        sg.Popup('Error: Zipcode not found')


if __name__ == '__main__':
    main()

#text = sg.PopupGetText('Title', 'Please input something')
#sg.Popup('Results', 'The value returned from PopupGetText', text)
