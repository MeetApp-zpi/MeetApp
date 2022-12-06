<script lang="ts">
    import Svelecte, { addFormatter, config } from 'svelecte';

    export let fetch;
    export let placeholder;
    export let inputId;
    export let selected;
    export let style = '';

    let options;

    function cityRenderer(item, isSelected) {
        return `${item.city}<span class='text-gray'>, ${item.voivodeship}</span>`;
    }

    function updateOptions(newArr) {
        options = [];
        for (let loc of newArr) {
            options.push({ id: loc.id, city: loc.city.name, voivodeship: loc.voivodeship.name });
        }
    }

    addFormatter('city-render', cityRenderer);

    config.i18n = {
        empty: 'Brak miast',
        nomatch: 'Brak pasujących miast',
        max: (num) => `Maksymalna liczba miast ${num} została wybrana`,
        fetchBefore: 'Wpisz żeby wyszukać',
        fetchQuery: (minQuery) => `Wpisz ${minQuery > 1 ? `minimum ${minQuery} znaki ` : ''}`,
        fetchEmpty: 'Brak pasujących danych',
        collapsedSelection: (count) => `${count} wybrano`,
        createRowLabel: (value) => `Stwórz '${value}'`
    };
</script>

<div class="svelecte-select">
    <Svelecte
        on:fetch={(e) => updateOptions(e.detail)}
        {options}
        renderer="city-render"
        minQuery={2}
        valueAsObject={true}
        resetOnBlur={true}
        fetchResetOnBlur={true}
        disableSifter={true}
        {style}
        {fetch}
        {placeholder}
        {inputId}
        bind:value={selected}
    />
</div>

<style>
    .svelecte-select :global(.sv-content) {
        overflow-x: scroll;
    }

    .svelecte-select :global(.sv-control) {
        background-color: var(--white);
        padding: 0.5rem 0.5rem 0.5rem 0.5rem;
        color: var(--gray);
        border-radius: 0.5rem;
        font-size: 1.5rem;
    }

    .svelecte-select :global(.inputBox) {
        height: 2rem;
        color: var(--gray);
    }

    .svelecte-select :global(.inputBox) {
        color: var(--cocoa);
    }
</style>
