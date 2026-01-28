# MODULE 6: TECH WRITING - WEEK 2 DETAILED CONTENT
## API Documentation

**Загальна тривалість:** Week 2 (Days 148-154)
**Щоденне навантаження:** 45-60 хвилин
**Загальний час тижня:** 25-30 годин

---

# ТИЖДЕНЬ 2: DOCUMENTING APIS
## Days 148-154: Детальний розклад

---

## ДЕНЬ 148: API Terminology

### Навчальні цілі дня:
- Endpoints, Methods, Payloads
- Precise language

### LESSON 148.1: Speaking REST (20 хвилин)
- **Endpoint:** The URL.
- **Method:** GET/POST.
- **Payload/Body:** Data sent.
- **Response:** Data received.

---

## ДЕНЬ 149: Documenting an Endpoint

### Навчальні цілі дня:
- Structure of an API Doc
- Parameters

### LESSON 149.1: The Recipe (20 хвилин)
For each endpoint, list:
1.  **Description:** "Gets user by ID."
2.  **URL:** `/users/:id`
3.  **Params:** `id` (Required, Integer).
4.  **Returns:** User Object.

---

## ДЕНЬ 150: Request & Response Examples

### Навчальні цілі дня:
- JSON Literacy
- "Copy-pastable" examples

### LESSON 150.1: Show, Don't Just Tell (15 хвилин)
Always provide a JSON snippet.
```json
{
  "id": 1,
  "name": "Alex"
}
```

---

## ДЕНЬ 151: Error Codes

### Навчальні цілі дня:
- HTTP Status Codes
- Explaining errors

### LESSON 151.1: 4xx vs 5xx (20 хвилин)
- **200 OK:** Success.
- **400 Bad Request:** User error (Invalid input).
- **401 Unauthorized:** No token.
- **500 Server Error:** Our fault.

---

## ДЕНЬ 152: OpenAPI (Styles)

### Навчальні цілі дня:
- Declarative Specs
- Modal verbs (Must/Should)

### LESSON 152.1: RFC 2119 Keywords (20 хвилин)
- **MUST:** Mandatory.
- **SHOULD:** Recommended.
- **MAY:** Optional.

---

## ДЕНЬ 153: Practice - Write an Endpoint Doc

### Task: The Login Endpoint
Document `POST /login`.
Input: email/password.
Output: JWT token.
Errors: 401 (Wrong password).

---

## ДЕНЬ 154: Weekly Review

### Review
- Are your examples valid JSON?
- Did you use "MUST" correctly?
