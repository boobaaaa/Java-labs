const API = {
  authors: '/api/authors',
  books: '/api/books',
  readers: '/api/readers',
  copies: '/api/copies',
  issues: '/api/issues',
  stats: '/api/stats'
};

function qs(sel){ return document.querySelector(sel); }
function el(tag, attrs = {}, ...children){
  const e = document.createElement(tag);
  for (const [k,v] of Object.entries(attrs)){
    if (k === 'class') e.className = v;
    else if (k.startsWith('on') && typeof v === 'function') e.addEventListener(k.substring(2), v);
    else if (v !== undefined && v !== null) e.setAttribute(k,v);
  }
  for (const c of children){
    if (c === null || c === undefined) continue;
    e.append(c.nodeType ? c : document.createTextNode(String(c)));
  }
  return e;
}

async function apiFetch(url, options={}){
  const res = await fetch(url, {
    headers: { 'Content-Type':'application/json', ...(options.headers||{}) },
    ...options
  });
  if (res.status === 204) return null;
  const text = await res.text();
  const body = text ? JSON.parse(text) : null;
  if (!res.ok) {
    const err = new Error(body?.message || `HTTP ${res.status}`);
    err.body = body;
    throw err;
  }
  return body;
}

function showError(msg, fieldErrors){
  const box = qs('#modalError');
  if (!msg) {
    box.classList.add('d-none');
    box.innerHTML = '';
    return;
  }
  box.classList.remove('d-none');
  let html = `<strong>${msg}</strong>`;
  if (fieldErrors){
    html += '<ul class="mb-0">' + Object.entries(fieldErrors).map(([k,v])=>`<li><code>${k}</code>: ${v}</li>`).join('') + '</ul>';
  }
  box.innerHTML = html;
}

const modal = new bootstrap.Modal(qs('#editModal'));
let currentSave = null;
qs('#saveBtn').addEventListener('click', async () => {
  if (currentSave) await currentSave();
});

function openModal(title, formEl, onSave){
  qs('#modalTitle').textContent = title;
  const form = qs('#modalForm');
  form.innerHTML = '';
  form.append(formEl);
  showError(null);
  currentSave = onSave;
  modal.show();
}

function inputRow(label, input){
  return el('div', {class:'mb-3'},
    el('label', {class:'form-label'}, label),
    input
  );
}

function textInput(name, value='', {type='text', required=false, placeholder='', maxLength}={}){
  const i = el('input', {class:'form-control', name, type, value: value ?? '', placeholder});
  if (required) i.required = true;
  if (maxLength) i.maxLength = maxLength;
  return i;
}

function numberInput(name, value='', {min, max}={}){
  const i = el('input', {class:'form-control', name, type:'number', value: value ?? ''});
  if (min !== undefined) i.min = String(min);
  if (max !== undefined) i.max = String(max);
  return i;
}

function dateInput(name, value=''){
  const i = el('input', {class:'form-control', name, type:'date', value: value ?? ''});
  return i;
}

function selectInput(name, options, value){
  const s = el('select', {class:'form-select', name});
  for (const opt of options){
    const o = el('option', {value: opt.value}, opt.label);
    if (String(opt.value) === String(value)) o.selected = true;
    s.append(o);
  }
  return s;
}

// ---------- Loaders ----------
async function refreshStats(){
  const st = await apiFetch(API.stats);
  qs('#statsBar').textContent = `Авторы: ${st.totalAuthors} · Книги: ${st.totalBooks} · Экземпляры: ${st.totalCopies} · Читатели: ${st.totalReaders} · Активные выдачи: ${st.activeIssues} · Просрочено: ${st.overdueIssues} · Доступно: ${st.availableCopies}`;
}

async function loadAuthors(){
  const authors = await apiFetch(API.authors);
  const tb = qs('#authorsTable tbody');
  tb.innerHTML = '';
  for (const a of authors){
    tb.append(el('tr', {},
      el('td', {}, a.id),
      el('td', {}, a.authorName),
      el('td', {}, a.country ?? ''),
      el('td', {class:'text-end'},
        el('button', {class:'btn btn-sm btn-outline-secondary me-2', onclick:()=>editAuthor(a)}, 'Изменить'),
        el('button', {class:'btn btn-sm btn-outline-danger', onclick:()=>deleteAuthor(a.id)}, 'Удалить')
      )
    ));
  }
}

async function loadBooks(){
  const books = await apiFetch(API.books);
  const tb = qs('#booksTable tbody');
  tb.innerHTML = '';
  for (const b of books){
    tb.append(el('tr', {},
      el('td', {}, b.id),
      el('td', {}, b.isbn ?? ''),
      el('td', {}, b.title),
      el('td', {}, b.authorName),
      el('td', {}, b.genre ?? ''),
      el('td', {}, b.yearPublished ?? ''),
      el('td', {}, b.publisher ?? ''),
      el('td', {class:'text-end'},
        el('button', {class:'btn btn-sm btn-outline-secondary me-2', onclick:()=>editBook(b)}, 'Изменить'),
        el('button', {class:'btn btn-sm btn-outline-danger', onclick:()=>deleteBook(b.id)}, 'Удалить')
      )
    ));
  }
}

async function loadReaders(){
  const readers = await apiFetch(API.readers);
  const tb = qs('#readersTable tbody');
  tb.innerHTML = '';
  for (const r of readers){
    tb.append(el('tr', {},
      el('td', {}, r.id),
      el('td', {}, r.fullName),
      el('td', {}, r.phone ?? ''),
      el('td', {}, r.registrationDate ?? ''),
      el('td', {}, r.status ?? ''),
      el('td', {class:'text-end'},
        el('button', {class:'btn btn-sm btn-outline-secondary me-2', onclick:()=>editReader(r)}, 'Изменить'),
        el('button', {class:'btn btn-sm btn-outline-danger', onclick:()=>deleteReader(r.id)}, 'Удалить')
      )
    ));
  }
}

async function loadCopies(){
  const copies = await apiFetch(API.copies);
  const tb = qs('#copiesTable tbody');
  tb.innerHTML = '';
  for (const c of copies){
    tb.append(el('tr', {},
      el('td', {}, c.inventoryNumber),
      el('td', {}, c.bookTitle),
      el('td', {}, c.status ?? ''),
      el('td', {}, c.receiptDate ?? ''),
      el('td', {}, c.location ?? ''),
      el('td', {class:'text-end'},
        el('button', {class:'btn btn-sm btn-outline-secondary me-2', onclick:()=>editCopy(c)}, 'Изменить'),
        el('button', {class:'btn btn-sm btn-outline-danger', onclick:()=>deleteCopy(c.inventoryNumber)}, 'Удалить')
      )
    ));
  }
}

async function loadIssues(){
  const issues = await apiFetch(API.issues);
  const tb = qs('#issuesTable tbody');
  tb.innerHTML = '';
  for (const i of issues){
    tb.append(el('tr', {},
      el('td', {}, i.id),
      el('td', {}, i.inventoryNumber),
      el('td', {}, i.bookTitle),
      el('td', {}, i.readerName),
      el('td', {}, i.issueDate),
      el('td', {}, i.plannedReturnDate),
      el('td', {}, i.actualReturnDate ?? ''),
      el('td', {}, i.issueStatus ?? ''),
      el('td', {class:'text-end'},
        el('button', {class:'btn btn-sm btn-outline-secondary me-2', onclick:()=>editIssue(i)}, 'Изменить'),
        el('button', {class:'btn btn-sm btn-outline-danger', onclick:()=>deleteIssue(i.id)}, 'Удалить')
      )
    ));
  }
}

async function refreshAll(){
  await Promise.all([refreshStats(), loadAuthors(), loadBooks(), loadReaders(), loadCopies(), loadIssues()]);
}

// ---------- CRUD handlers ----------
async function addAuthor(){ editAuthor(null); }
function editAuthor(a){
  const name = textInput('authorName', a?.authorName ?? '', {required:true, maxLength:100});
  const country = textInput('country', a?.country ?? '', {maxLength:50});
  const form = el('div', {},
    inputRow('Имя автора*', name),
    inputRow('Страна', country)
  );
  openModal(a ? 'Редактировать автора' : 'Добавить автора', form, async () => {
    try {
      const payload = { authorName: name.value.trim(), country: country.value.trim() || null };
      if (a) await apiFetch(`${API.authors}/${a.id}`, {method:'PUT', body: JSON.stringify(payload)});
      else await apiFetch(API.authors, {method:'POST', body: JSON.stringify(payload)});
      modal.hide();
      await refreshAll();
    } catch (e) {
      showError(e.message, e.body?.fieldErrors);
    }
  });
}
async function deleteAuthor(id){
  if (!confirm('Удалить автора?')) return;
  try { await apiFetch(`${API.authors}/${id}`, {method:'DELETE'}); await refreshAll(); }
  catch(e){ alert(e.message); }
}

async function addBook(){ editBook(null); }
async function editBook(b){
  const authors = await apiFetch(API.authors);
  const authorSel = selectInput('authorId', authors.map(a=>({value:a.id, label:`${a.authorName} (ID ${a.id})`})), b?.authorId ?? authors[0]?.id);
  const isbn = textInput('isbn', b?.isbn ?? '', {maxLength:20});
  const title = textInput('title', b?.title ?? '', {required:true, maxLength:200});
  const genre = textInput('genre', b?.genre ?? '', {maxLength:50});
  const year = numberInput('yearPublished', b?.yearPublished ?? '', {min:0, max:2100});
  const publisher = textInput('publisher', b?.publisher ?? '', {maxLength:100});

  const form = el('div', {},
    inputRow('Автор*', authorSel),
    inputRow('Название*', title),
    inputRow('ISBN', isbn),
    inputRow('Жанр', genre),
    inputRow('Год', year),
    inputRow('Издательство', publisher)
  );

  openModal(b ? 'Редактировать книгу' : 'Добавить книгу', form, async () => {
    try {
      const payload = {
        authorId: Number(authorSel.value),
        title: title.value.trim(),
        isbn: isbn.value.trim() || null,
        genre: genre.value.trim() || null,
        yearPublished: year.value ? Number(year.value) : null,
        publisher: publisher.value.trim() || null
      };
      if (b) await apiFetch(`${API.books}/${b.id}`, {method:'PUT', body: JSON.stringify(payload)});
      else await apiFetch(API.books, {method:'POST', body: JSON.stringify(payload)});
      modal.hide();
      await refreshAll();
    } catch (e) {
      showError(e.message, e.body?.fieldErrors);
    }
  });
}
async function deleteBook(id){
  if (!confirm('Удалить книгу?')) return;
  try { await apiFetch(`${API.books}/${id}`, {method:'DELETE'}); await refreshAll(); }
  catch(e){ alert(e.message); }
}

async function addReader(){ editReader(null); }
function editReader(r){
  const fullName = textInput('fullName', r?.fullName ?? '', {required:true, maxLength:100});
  const phone = textInput('phone', r?.phone ?? '', {maxLength:20});
  const reg = dateInput('registrationDate', r?.registrationDate ?? '');
  const status = textInput('status', r?.status ?? 'Активен', {maxLength:20});
  const form = el('div', {},
    inputRow('ФИО*', fullName),
    inputRow('Телефон', phone),
    inputRow('Дата регистрации', reg),
    inputRow('Статус', status)
  );
  openModal(r ? 'Редактировать читателя' : 'Добавить читателя', form, async () => {
    try {
      const payload = {
        fullName: fullName.value.trim(),
        phone: phone.value.trim() || null,
        registrationDate: reg.value || null,
        status: status.value.trim() || null
      };
      if (r) await apiFetch(`${API.readers}/${r.id}`, {method:'PUT', body: JSON.stringify(payload)});
      else await apiFetch(API.readers, {method:'POST', body: JSON.stringify(payload)});
      modal.hide();
      await refreshAll();
    } catch (e) {
      showError(e.message, e.body?.fieldErrors);
    }
  });
}
async function deleteReader(id){
  if (!confirm('Удалить читателя?')) return;
  try { await apiFetch(`${API.readers}/${id}`, {method:'DELETE'}); await refreshAll(); }
  catch(e){ alert(e.message); }
}

async function addCopy(){ editCopy(null); }
async function editCopy(c){
  const books = await apiFetch(API.books);
  const bookSel = selectInput('bookId', books.map(b=>({value:b.id, label:`${b.title} (ID ${b.id})`})), c?.bookId ?? books[0]?.id);
  const status = textInput('status', c?.status ?? 'В наличии', {maxLength:20});
  const receipt = dateInput('receiptDate', c?.receiptDate ?? '');
  const location = textInput('location', c?.location ?? '', {maxLength:50});
  const form = el('div', {},
    inputRow('Книга*', bookSel),
    inputRow('Статус', status),
    inputRow('Дата поступления', receipt),
    inputRow('Расположение', location)
  );
  openModal(c ? 'Редактировать экземпляр' : 'Добавить экземпляр', form, async () => {
    try {
      const payload = {
        bookId: Number(bookSel.value),
        status: status.value.trim() || null,
        receiptDate: receipt.value || null,
        location: location.value.trim() || null
      };
      if (c) await apiFetch(`${API.copies}/${c.inventoryNumber}`, {method:'PUT', body: JSON.stringify(payload)});
      else await apiFetch(API.copies, {method:'POST', body: JSON.stringify(payload)});
      modal.hide();
      await refreshAll();
    } catch (e) {
      showError(e.message, e.body?.fieldErrors);
    }
  });
}
async function deleteCopy(inv){
  if (!confirm('Удалить экземпляр?')) return;
  try { await apiFetch(`${API.copies}/${inv}`, {method:'DELETE'}); await refreshAll(); }
  catch(e){ alert(e.message); }
}

async function addIssue(){ editIssue(null); }
async function editIssue(i){
  const copies = await apiFetch(API.copies);
  const readers = await apiFetch(API.readers);
  const copySel = selectInput('inventoryNumber', copies.map(c=>({value:c.inventoryNumber, label:`${c.inventoryNumber} — ${c.bookTitle} (${c.status})`})), i?.inventoryNumber ?? copies[0]?.inventoryNumber);
  const readerSel = selectInput('readerId', readers.map(r=>({value:r.id, label:`${r.fullName} (ID ${r.id})`})), i?.readerId ?? readers[0]?.id);
  const issueDate = dateInput('issueDate', i?.issueDate ?? new Date().toISOString().slice(0,10));
  const planned = dateInput('plannedReturnDate', i?.plannedReturnDate ?? '');
  const actual = dateInput('actualReturnDate', i?.actualReturnDate ?? '');
  const status = textInput('issueStatus', i?.issueStatus ?? 'Активна', {maxLength:20});

  const form = el('div', {},
    inputRow('Экземпляр*', copySel),
    inputRow('Читатель*', readerSel),
    inputRow('Дата выдачи*', issueDate),
    inputRow('Планируемый возврат*', planned),
    inputRow('Фактический возврат', actual),
    inputRow('Статус', status)
  );

  openModal(i ? 'Редактировать выдачу' : 'Новая выдача', form, async () => {
    try {
      const payload = {
        inventoryNumber: Number(copySel.value),
        readerId: Number(readerSel.value),
        issueDate: issueDate.value,
        plannedReturnDate: planned.value,
        actualReturnDate: actual.value || null,
        issueStatus: status.value.trim() || null
      };
      if (i) await apiFetch(`${API.issues}/${i.id}`, {method:'PUT', body: JSON.stringify(payload)});
      else await apiFetch(API.issues, {method:'POST', body: JSON.stringify(payload)});
      modal.hide();
      await refreshAll();
    } catch (e) {
      showError(e.message, e.body?.fieldErrors);
    }
  });
}
async function deleteIssue(id){
  if (!confirm('Удалить выдачу?')) return;
  try { await apiFetch(`${API.issues}/${id}`, {method:'DELETE'}); await refreshAll(); }
  catch(e){ alert(e.message); }
}

// ---------- Wire buttons ----------
qs('#addAuthorBtn').addEventListener('click', addAuthor);
qs('#addBookBtn').addEventListener('click', addBook);
qs('#addReaderBtn').addEventListener('click', addReader);
qs('#addCopyBtn').addEventListener('click', addCopy);
qs('#addIssueBtn').addEventListener('click', addIssue);

refreshAll().catch(e => alert(e.message));
