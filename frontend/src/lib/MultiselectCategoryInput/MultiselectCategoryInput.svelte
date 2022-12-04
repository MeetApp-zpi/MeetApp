<script lang="ts">
    import Svelecte, { config } from 'svelecte';

    export let data;
    export let placeholder;
    export let inputId;
    export let selected;
    export let style;

    const i18n = {
        empty: 'Brak kategorii',
        nomatch: 'Brak pasujących kategorii',
        max: (num) => `Maksymalna liczba kategorii ${num} została wybrana`,
        fetchBefore: 'Wpisz żeby wyszukać',
        fetchQuery: (minQuery) => `Wpisz ${minQuery > 1 ? `minimum ${minQuery} znaki ` : ''}`,
        fetchEmpty: 'Brak pasujących danych',
        collapsedSelection: (count) => `${count} wybrano`,
        createRowLabel: (value) => `Stwórz '${value}'`
    };
</script>

<div class="svelecte-multiselect categorySvelecteControl categorySvelecteContent">
    <Svelecte {style} options={data} {placeholder} {inputId} multiple="true" bind:value={selected} {i18n} />
</div>

<style>
    .svelecte-multiselect :global(.sv-content) {
        overflow-x: scroll;
    }

    .categorySvelecteControl :global(.sv-control) {
        border-color: var(--pickle);
        background-color: var(--white);
        border-width: 2px;
        padding: 0.5rem 0.5rem 0.5rem 0.5rem;
        color: var(--gray);
        border-radius: 0.5rem;
        font-size: 1.5rem;
        /* line-height: 2rem; */
    }

    .categorySvelecteContent :global(.inputBox) {
        height: 2rem;
        color: var(--gray);
    }
</style>
