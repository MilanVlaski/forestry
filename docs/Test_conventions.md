# What to test?
- All query functions that require strings, and not JpaRepository functions. - easy to make a SQL syntax error
- Trees#addTree - core use case
- Arborist CANNOT insert forests (etc) - security feature 
- uniqueness constraints handled with appropriate friendly message
