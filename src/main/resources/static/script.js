let sortColumn = 'id';
let sortOrder = 'asc';

function renderTable() {
    const tbody = document.querySelector('#dataTable tbody');
    tbody.innerHTML = '';

    fetch("/data/computers?sort=" + sortColumn + "," + sortOrder)
        .then(response => {
            if (!response.ok) {
                return response.text().then(errorText => {
                    alert(errorText);
                });
            } else { return response.json(); }
        })
        .then(data => {
            data.forEach(row => {
                const tr = document.createElement('tr');

                const driveData = (row.hardDrive === null) ? "" : row.hardDrive.hardDriveType + ', ' + row.hardDrive.capacity + 'GB';
                const processorData = (row.processor === null) ? "" : row.processor.model + ', ' + row.processor.freq + 'GHz, ' + row.processor.cores + 'rdz, ' + row.processor.threads + 'wąt';
                const ramData = (row.ram === null) ? "" : row.ram.ramNumber + 'GB, ' + row.ram.ramType;
                const screenData = (row.screen === null) ? "" : row.screen.resolutionX + 'px, ' + row.screen.resolutionY + 'px, ' + row.screen.screenType;

                tr.innerHTML = `
                <td>${row.id}</td>
                <td contenteditable="true" data-original="${row.brandName}" oninput="updateOnChange(this)">${row.brandName}</td>
                <td contenteditable="true" data-original="${row.computerName}" oninput="updateOnChange(this)">${row.computerName}</td>
                <td contenteditable="true" data-original="${driveData}" oninput="updateOnChange(this)">${driveData}</td>
                <td contenteditable="true" data-original="${processorData}" oninput="updateOnChange(this)">${processorData}</td>
                <td contenteditable="true" data-original="${ramData}" oninput="updateOnChange(this)">${ramData}</td>
                <td contenteditable="true" data-original="${screenData}" oninput="updateOnChange(this)">${screenData}</td>
                <td>
                    <button onclick="deleteRow(${row.id})">Usuń</button>
                    <button class="save-btn" onclick="saveRow(parseRow(this, ${row.id}), 'PUT', '/data/updateComputer')">Zatwierdź</button>
                </td>
            `;
                tr.querySelector('.save-btn').disabled = true;
                tbody.appendChild(tr);
            });
        });
}

function addRow() {
    const tbody = document.querySelector('#dataTable tbody');
    const tr = document.createElement('tr');
    tr.innerHTML = `
    <td>-</td>
    <td contenteditable="true"></td>
    <td contenteditable="true"></td>
    <td contenteditable="true">"typ", "pojemność"</td>
    <td contenteditable="true">"model", "częstoliwość", "rdzenie", "wątki"</td>
    <td contenteditable="true">"Ilość", "Typ"</td>
    <td contenteditable="true">"X", "Y", "Typ"</td>
    <td><button onclick="saveRow(parseRow(this, null), 'POST', '/data/addComputer')">Zatwierdź</button></td>
    `
    tbody.appendChild(tr);
}

function parseRow(button, id) {
    const cells = button.closest('tr').querySelectorAll('td[contenteditable]');

    const hardDriveData = cells[2].innerText.trim().replaceAll("GB", "").split(", ");
    const processorData = cells[3].innerText.trim().replaceAll(/GHz|rdz|wąt/g, "").split(", ");
    const ramData = cells[4].innerText.trim().replaceAll("GB", "").split(", ");
    const screenData = cells[5].innerText.trim().replaceAll("px", "").split(", ");

    const res = {
        brandName: cells[0].innerText.trim(),
        computerName: cells[1].innerText.trim()
    };
    if (id !== null) { res.id = id; }
    if (processorData.length === 4) {
        res.processor = {
            model: processorData[0],
            freq: processorData[1],
            cores: processorData[2],
            threads: processorData[3]
        };
    } else { res.processor = null; }
    if (ramData.length === 2) {
        res.ram = {
            ramNumber: ramData[0],
            ramType: ramData[1]
        };
    }
    else { res.ram = null; }
    if (screenData.length === 3) {
        res.screen = {
            resolutionX: screenData[0],
            resolutionY: screenData[1],
            screenType: screenData[2]
        };
    }
    else { res.screen = null; }
    if (hardDriveData.length === 2) {
        res.hardDrive = {
            hardDriveType: hardDriveData[0],
            capacity: hardDriveData[1]
        }
    } else { res.hardDrive = null; }
    return res;
}

function saveRow(data, sendMethod, url) {
    fetch(url, {
        method: sendMethod,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(response => {
        if (!response.ok) {
            return response.text().then(errorText => {
                throw new Error(errorText);
            });
        }
    }).then(_ => {
            renderTable();
    }).catch(error => {
        alert(error.message);
    });
}

function deleteRow(id) {
    fetch('/data/deleteComputer/' + id, {
        method: 'DELETE'
    }).then(response => {
        if (!response.ok) {
            return response.text().then(errorText => {
                throw new Error(errorText);
            });
        }
    }).then(_ => {
        renderTable();
    }).catch(error => {
        alert(error.message);
    })
}

function updateOnChange(td) {
    const tr = td.closest('tr');
    const cells = tr.querySelectorAll('td[contenteditable]');

    let changed = false;

    cells.forEach(cell => {
        const original = cell.getAttribute('data-original')?.trim();
        const current = cell.innerText.trim();
        if (original !== current) {
            changed = true;
        }
    });

    let saveBtn = tr.querySelector('.save-btn');
    saveBtn.disabled = !changed;
}

function sortTable(field) {
    if (sortColumn === field) {
        if (sortOrder === 'asc') { sortOrder = 'desc'; }
        else { sortOrder = 'asc'; }
    } else {
        sortColumn = field;
        sortOrder = 'asc';
    }
    renderTable();
}
renderTable();