import csv

if __name__ == "__main__":
    records = []
    voivodeships = set()
    cities = set()

    with open("countryExtract.tsv", encoding='UTF-8') as f:
        records = f.readlines()

    newLines = []
    cities_voivodeships = set()
    for record in records[1:]:
        splitted = record.split('\t')
        if splitted[5] != '':
            voivodeship = splitted[6].strip().split(' ')[1]
            testPair = splitted[5] + "," + voivodeship
            if testPair not in cities_voivodeships:
                newRecord = []
                newRecord.append(splitted[5])
                newRecord.append(voivodeship)
                newRecord.append(splitted[2])
                newRecord.append(splitted[1])
                newLines.append(newRecord)
                cities_voivodeships.add(testPair)

    for re in newLines:
        if ',' in re[0]:
            print(re)

    with open("cities.csv", "w", newline='') as f:
        writer = csv.writer(f, delimiter=',')
        writer.writerows(newLines)
