import os, re

with open('zero_coverage_models.txt', 'r') as f:
    zero_models = set(line.strip() for line in f if line.strip())

client_dir = 'src/main/java/lol/pbu/z4j/client/'
spec_dir = 'src/test/groovy/lol/pbu/z4j/client/'

missing_endpoints_by_spec = {}

for filename in os.listdir(client_dir):
    if filename.endswith('Client.java'):
        with open(os.path.join(client_dir, filename), 'r') as f:
            content = f.read()
            
        spec_name = filename.replace('Client.java', 'ClientSpec.groovy')
        
        # find all methods: Mono<@Valid SomeResponse> someMethod(
        methods = re.findall(r'Mono<@Valid\s+([A-Za-z0-9_]+)>\s+([A-Za-z0-9_]+)\(', content)
        for return_type, method_name in methods:
            if return_type in zero_models:
                if spec_name not in missing_endpoints_by_spec:
                    missing_endpoints_by_spec[spec_name] = []
                missing_endpoints_by_spec[spec_name].append((method_name, return_type))

print(f"Found {len(missing_endpoints_by_spec)} Spec files to update.")
for spec, endpoints in missing_endpoints_by_spec.items():
    print(f"\n{spec}:")
    for method, ret in set(endpoints): # unique methods
        print(f"  - {method}() -> {ret}")
