<template>
    <div class="all-action-elements">
        <div class="common-layer">
            <h2 class="screens-text-above">Screens</h2>
            <div class="layer-above-scroll">
                <div class="horizontal-scroll">
                    <div v-for="image in images" :key="image.id" class="scroll-container" ref="scrollContainer">
                        <div>
                            <div>
                                <a :href="image.content" :data-lightbox="image.name">
                                    <img :src="image.content" alt="Image"
                                         class="picture">
                                </a>
                                <p class="image-label">{{ formatText(image.name) }}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="search-container">
            <label class="search-label" for="search-input">Поиск кадра</label>
            <div class="text-field">
                <input id="search-input" placeholder="01:54" class="search-input"
                       v-model="screenSearchTime"/>
                <div class="valid-search" v-if="!notValidTime">
                    <button class="button-search" @click="reloadImages()">Получить</button>
                </div>
            </div>

            <div v-if="notValidTime" class="not-valid-container">
                <p class="not-valid-time-error">Введено некорректное значение времени</p>
            </div>
        </div>
    </div>


</template>

<script>
import {checkImgUrl} from "@/scripts/server";
import {timeWithoutExtension, validateHhMm, timeIntoNumber} from "@/scripts/player_formatting";

export default {
    data() {
        return {
            images: [],
            currentImageUrl: '',
            screenSearchTime: '',
            notValidTime: false
        };
    },
    async mounted() {
        try {
            this.images = await this.fetchImages();
        } catch (error) {
            console.error('Failed to get images!', error);
        }
    },
    methods: {
        /**
         * @returns {Promise<*>} разрешается в ассоциативный массив(keys: id, content, mime_type, name, size)
         */
        async fetchImages() {
            try {
                const response = await fetch("http://localhost:8080/video/all_images");
                const data = await response.json();
                const result = data.reduce((acc, image) => {
                    //кодировка у создававаемой для картинки ссылки base64.
                    //Данные идут на сервер и хранятся в бд без этого "префикса"
                    image.content = "data:image/jpeg;base64," + image.content;
                    checkImgUrl(image.content);

                    acc[image.id] = image;
                    return acc;
                }, {});

                return result;
            } catch (error) {
                throw new Error('Failed to get images! ' + error);
            }
        },
        //возвращает время без расширения .jpeg(которое идёт с image.name)
        formatText(timeWithExtension) {
            return timeWithoutExtension(timeWithExtension);
        },

        /**
         * возвращает array[n]?array[0] картинок в случае успешной валидации пользовательского ввода времени
         * @returns {Promise<void>}
         */
        async reloadImages() {
            let value = timeIntoNumber(this.screenSearchTime);

            let tempArray = {};
            let i = 0;
            for (let img in this.images) {
                let currentImageNumberTime = timeIntoNumber(timeWithoutExtension(this.images[img].name));
                if (currentImageNumberTime === value) {
                    tempArray[i] = this.images[img]
                    i++;
                }
            }

            this.images = tempArray;
        }
    },

    watch: {
        /**
         * проверяет, прошла ли клиентская строка времени валидацию(00:54)
         * в случае отстутствия любых символов возвращает весь массив скринов
         * @param newTime клиентская строка времени
         * @returns {Promise<void>}
         */
        async screenSearchTime(newTime) {
            if (this.screenSearchTime === '') {
                this.notValidTime = false;
                this.images = await this.fetchImages();
            } else {
                this.notValidTime = !validateHhMm(newTime + "");
            }

        },

    }
};
</script>


<style scoped lang="scss">
$frame-label-color: black;
$font-text: sans;

.common-layer {
  height: 100%;
  background: lavender;
  color: #fff;
  font-family: $font-text, serif;
  font-size: 18px;
  margin: 40px 15px 15px;
  border: 5px solid $frame-label-color;
  padding: 20px;
  border-radius: 10px/20px;
}

.screens-text-above {
  font-weight: 700;
  text-align: center;
  color: $frame-label-color;
}

.image-label {
  color: $frame-label-color;
  font-weight: 530;
  text-align: center;
}

.layer-above-scroll {
  padding: 5px;
}

.horizontal-scroll {
  width: 100%;
  overflow-x: auto;
  white-space: nowrap;
}

.scroll-container {
  display: inline-block;
}

.picture {
  width: 360px;
  height: 240px;
  margin: 2px;
}

.search-container {
  margin: 30px;
  text-align: center;
  width: 100%;
}

.search-input {
  border-radius: 5px;
  border: 3px solid $frame-label-color;
  margin-top: 20px;
}

.search-label {
  font-size: 20px;
  font-weight: 500;
  font-family: $font-text, sans;
}

.not-valid-container {
  border: red;
  margin: 20px;
  padding: 5px;
}

.not-valid-time-error {
  color: red;
  font-size: 14px;
}

.valid-search {
  margin: 10px;
  display: block;
}

.button-search {
  padding: 5px;
  margin-left: 5px;
  border-radius: 10px;
}

</style>