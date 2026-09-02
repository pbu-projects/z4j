categories = {
    "Low Effort (Standard Lists & Enums)": [],
    "Medium Effort (Dependent Data / Setup Required)": [],
    "High Effort (Enterprise / Async / Incremental)": []
}

with open('zero_coverage_models.txt', 'r') as f:
    models = [line.strip() for line in f if line.strip()]

for m in models:
    if any(keyword in m for keyword in ['Incremental', 'Batch', 'Bulk', 'SkillBased', 'Export', 'SLA', 'Cursor', 'TimeBased', 'Definition', 'Diff']):
        categories["High Effort (Enterprise / Async / Incremental)"].append(m)
    elif any(keyword in m for keyword in ['CustomObject', 'Itam', 'Suspended', 'Deleted', 'Twitter', 'OAuth', 'GlobalClient', 'Recover']):
        categories["Medium Effort (Dependent Data / Setup Required)"].append(m)
    else:
        categories["Low Effort (Standard Lists & Enums)"].append(m)

with open('uncovered_model_testing_effort.md', 'w') as out:
    out.write("# Uncovered Zendesk Models: Testing Effort Analysis\n\n")
    out.write("Because we bypassed these models in our integration test suite, they sit at 0% coverage. To hit >80% model coverage naturally, we must force the Zendesk API to return payloads that match these structures.\n\n")
    
    for cat, items in categories.items():
        out.write(f"## {cat} ({len(items)} models)\n")
        
        if "Low Effort" in cat:
            out.write("These are standard read-only resources, enums, or basic list responses. We can cover these simply by appending a `GET` call to the end of our existing specs. The Sandbox should inherently return default data (or empty lists) that exercise the deserializer.\n")
        elif "Medium Effort" in cat:
            out.write("These require specific parent data to exist first. For example, we cannot list Custom Object Records without first creating a Custom Object schema, or we cannot test Suspended Tickets without intentionally triggering a suspension.\n")
        else:
            out.write("These represent Enterprise-only features, complex asynchronous batch jobs, or cursor-based incremental syncs. They require complex payload construction, polling, or specific account plans to execute natively.\n")
            
        out.write("\n```text\n")
        for item in sorted(items):
            out.write(f"- {item}\n")
        out.write("```\n\n")
