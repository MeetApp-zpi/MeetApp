if __name__ == "__main__":
    records = []
    voivodeships = set()
    cities = set()

    with open("countryExtract.tsv", encoding='UTF-8') as f:
        records = f.readlines()

    newLines = []
    for record in records[1:]:
        splitted = record.split('\t')
        if splitted[5] != '':
            newRecord = ""
            newRecord += splitted[5] + ','
            voivodeship = splitted[6].strip().split(' ')[1]
            newRecord += voivodeship + ','
            newRecord += splitted[2] + ','
            newRecord += splitted[1]
            newLines.append(newRecord)

    with open("cities.csv", "w") as f:
        f.writelines(newLines)
