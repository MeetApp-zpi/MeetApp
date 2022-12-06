<script lang="ts">
    import Svelecte, { addFormatter, config } from 'svelecte';

    export let fetch;
    export let placeholder;
    export let inputId;
    export let selected;
    export let style;

    let options = [];

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

    const i18n = {
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

<div class="svelecte-multiselect">
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
        multiple="true"
        bind:value={selected}
        {i18n}
    />
</div>

<style>
    .svelecte-multiselect :global(.sv-content) {
        overflow-x: scroll;
    }
</style>
