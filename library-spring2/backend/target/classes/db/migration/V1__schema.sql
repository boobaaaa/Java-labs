CREATE TABLE IF NOT EXISTS authors (
    id_author SERIAL PRIMARY KEY,
    author_name VARCHAR(100) NOT NULL,
    country VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS books (
    id_book SERIAL PRIMARY KEY,
    isbn VARCHAR(20) UNIQUE,
    title VARCHAR(200) NOT NULL,
    id_author INTEGER NOT NULL,
    genre VARCHAR(50),
    year_published INTEGER,
    publisher VARCHAR(100),
    CONSTRAINT fk_books_author FOREIGN KEY (id_author) REFERENCES authors(id_author)
);

CREATE TABLE IF NOT EXISTS readers (
    id_reader SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    registration_date DATE,
    status VARCHAR(20) DEFAULT 'Активен'
);

CREATE TABLE IF NOT EXISTS copies (
    inventory_number SERIAL PRIMARY KEY,
    id_book INTEGER NOT NULL,
    status VARCHAR(20) DEFAULT 'В наличии',
    receipt_date DATE,
    location VARCHAR(50),
    CONSTRAINT fk_copies_book FOREIGN KEY (id_book) REFERENCES books(id_book)
);

CREATE TABLE IF NOT EXISTS issues (
    id_issue SERIAL PRIMARY KEY,
    inventory_number INTEGER NOT NULL,
    id_reader INTEGER NOT NULL,
    issue_date DATE NOT NULL,
    planned_return_date DATE NOT NULL,
    actual_return_date DATE NULL,
    issue_status VARCHAR(20) DEFAULT 'Активна',
    CONSTRAINT fk_issues_copy FOREIGN KEY (inventory_number) REFERENCES copies(inventory_number),
    CONSTRAINT fk_issues_reader FOREIGN KEY (id_reader) REFERENCES readers(id_reader)
);
