import json


infile = open("books_updated.json", "r")

raw_data = json.load(infile)

# Use a set to keep track of unique names
unique_names = set()

# List to store unique entries
unique_data = []

# Iterate through the data
for entry in raw_data:
    # Check if the name is already in the set
    if entry["name"] not in unique_names:
        # If not, add the name to the set and add the entry to the unique_data list
        unique_names.add(entry["name"])
        unique_data.append(entry)

print("Before remove duplications: ", len(raw_data))
print("After remove duplications: ", len(unique_data))

# unique_data now contains entries with unique names
with open("proccessed_data.json", "w") as outfile:
    json.dump(unique_data, outfile, ensure_ascii=False, indent=4)